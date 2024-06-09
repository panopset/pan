package com.panopset.blackjackEngine

import com.panopset.compat.*
import java.io.StringWriter
import java.lang.RuntimeException
import java.util.*

open class BlackjackGameEngine(private val logDisplayer: LogDisplayer, val config: BlackjackConfiguration) {
    constructor(config: BlackjackConfiguration) : this(LogzDisplayerCMD, config)

    /**
     * Only needed for JUnit tests.
     */
    fun getShoe(): BlackjackShoe {
        return BlackjackShoe
    }

    var metrics = Metrics()
    val ct = CycleController(this)
    var strategy = Strategy(config)
    var countingSystems = CountingSystems(config)
    private val msg: BlackjackMessages = config.messages

    val bankroll = Bankroll()

    open fun exec(action: String) {
        Logz.logzDsiplayer = logDisplayer
        frontEndPreInitCheck()
        performAction(action)
        getNewActionCycleSnapshot(action)
    }

    fun frontEndPreInitCheck() {
        if (bankroll.reloadAmount == 0) {
            reset()
        }
    }

    private fun cloneState(action: String): BlackjackGameState {
        val players = ct.clonePlayers()
        return BlackjackGameState(
            bankroll.getChips(),
            bankroll.reloadCount,
            config.reloadAmountInWholeDollars * 100,
            action,
            ct.cloneDealer(),
            players,
            Metrics(metrics),
            getNextBet(),
            mistakeHeader,
            mistakeMessage,
            dealerMessage,
            getGameStatusVertical(),
            getGameStatusHorizontal(),
            getStatusChipsVertical(),
            getStatusChipsHorizontal(),
            bankroll.getStakeIncludingHands(players)
        )
    }

    fun deal(isShowing: Boolean): BlackjackCard {
        return BlackjackShoe.deal(isShowing, countingSystems)
    }

    private fun reset() {
        bankroll.reloadAmount = config.reloadAmountInWholeDollars * 100
        if (bankroll.reloadAmount < 0) {
            throw RuntimeException()
        }
        metrics.reset()
        ct.reset()
        bankroll.reset()
        countingSystems.resetCount()
        BlackjackShoe.numberOfDecks = config.decks
        resetNextBet()
        BlackjackShoe.shuffle()
        Logz.clear()
    }

    private fun performAction(action: String) {
        if (performAdminAction(action)) {
            return
        }
        val ra = getCycle().getRecommendedAction()
        if (!isPossibleAction(action, ra)) {
            return
        }
        var recommendedActionHeader = ""
        var recommendedActionStrategyLine = ""
        if (action != ra) {
            val sl = getCycle().getStrategyLine()
            if (sl != null) {
                recommendedActionStrategyLine = sl.source
                recommendedActionHeader = strategy.getHeaderFor(sl.stratCat)
            }
        }
        if (CMD_DEAL == action) {
            dealNextHand()
        }
        var player = checkForFinish()
        if (player != null) {
            val handPlayer = player.activeHand
            if (performAction(player, handPlayer, action)) {
                if (handPlayer != null) {
                    handPlayer.action = action
                }
                player = checkForFinish()
                updateMessages(recommendedActionHeader, recommendedActionStrategyLine)
            }
        }
        reasonThisGameExists(player)
    }

    private fun dealNextHand() {
        if (isShuffleNeeded) {
            shuffle()
            countingSystems.resetCount()
        }
        if (getCycle().isDealt) {
            ct.reset()
        }
        getCycle().deal()
    }

    /**
     * If you are practicing the basic strategy, you don't care about the outcome of the hand, just
     * stop the game and flag the player if there is a mistake, otherwise just keep going on to the
     * next hand, no need to click deal.
     *
     * @param player Player.
     */
    private fun reasonThisGameExists(player: Player?) {
        if (player == null && config.isFastDeal
            && !isAutomaticRunning() && "" == mistakeMessage) {
            exec(CMD_DEAL)
        }
    }

    private fun checkForFinish(): Player? {
        val player = getCycle().getActivePlayer()
        if (player == null) {
            getCycle().finish()
        }
        return player
    }

    private fun performAdminAction(action: String): Boolean {
        clearMessages()
        return when (action) {
            CMD_RESET -> {
                reset()
                dealerMessage = msg.resetMsg
                true
            }

            CMD_COUNT -> {
                config.toggleShowCount()
                true
            }

            CMD_SHUFFLE -> {
                if (ct.isActive) {
                    dealerMessage = String.format("%s.", msg.handActiveMsg)
                    return true
                }
                shuffle()
                true
            }

            CMD_INCREASE -> {
                increase()
                true
            }

            CMD_DECREASE -> {
                decrease()
                true
            }

            CMD_AUTO -> {
                auto()
                true
            }

            else -> false
        }
    }

    private fun hit(handPlayer: HandPlayer) {
        handPlayer.dealCard(BlackjackShoe.deal(true, countingSystems))
        if (handPlayer.isBusted()) {
            handPlayer.message = msg.bustedMsg
            handPlayer.wager.lost()
        }
    }

    private fun stand(hand: HandPlayer) {
        hand.stand()
    }

    private fun surrender(handPlayer: HandPlayer?): Boolean {
        if (!config.isLateSurrenderAllowed) {
            dealerMessage = msg.surrenderNotAllowedMsg
            return false
        }
        if (handPlayer != null) {
            if (!handPlayer.isInitialDeal) {
                dealerMessage = msg.surrenderImpossibleMsg
                return false
            }
        }
        handPlayer?.surrender()
        return true
    }

    private fun split(player: Player, handPlayer: HandPlayer): Boolean {
        if (!handPlayer.isCardFacesSplittableIncludeMessage) {
            dealerMessage = Logz.getPriorMessage()
            return false
        }
        bankroll.subtract(handPlayer.wager.initialBet)
        val splitHand = HandPlayer( Wager(handPlayer.wager))
        val splitCard = handPlayer.remove(1)
        handPlayer.dealCard(deal(true))
        handPlayer.setSplit()
        if (handPlayer.cards[0].isAce && !(config.isResplitAcesAllowed && handPlayer.cards[1].isAce)) {
            if (!config.isSplitAcePlayable) {
                handPlayer.stand()
            }
        }
        splitHand.dealCard(splitCard)
        splitHand.dealCard(deal(true))
        splitHand.setSplit()
        splitHand.action = CMD_SPLIT
        player.hands.add(splitHand)
        if (splitCard.isAce) {
            if (!(config.isResplitAcesAllowed && splitHand.cards[1].isAce)) {
                if (!config.isSplitAcePlayable) {
                    splitHand.stand()
                }
            }
        }
        if (handPlayer.value == 21) {
            handPlayer.stand()
        }
        if (splitHand.value == 21) {
            splitHand.stand()
        }
        return true
    }

    private fun dbl(handPlayer: HandPlayer): Boolean {
        if (!handPlayer.canDouble(config.isDoubleAfterSplitAllowed)) {
            dealerMessage = msg.doubleImpossibleMsg
            return false
        }
        handPlayer.dealCard(BlackjackShoe.deal(true, countingSystems))
        handPlayer.stand()
        bankroll.subtract(handPlayer.wager.initialBet)
        doubleDownWager(handPlayer)
        return true
    }

    private fun doubleDownWager(handPlayer: HandPlayer) {
        handPlayer.wager.doubleDown()
    }

    private fun performAction(player: Player, handPlayer: HandPlayer?, action: String): Boolean {
        if (handPlayer == null) {
            return false
        }
        return when (action) {
            CMD_SURRENDER -> surrender(handPlayer)
            CMD_HIT -> {
                hit(handPlayer)
                true
            }

            CMD_DOUBLE -> dbl(handPlayer)
            CMD_SPLIT -> split(player, handPlayer)
            CMD_STAND -> {
                stand(handPlayer)
                true
            }

            else -> false
        }
    }

    private fun isPossibleAction(action: String, ra: String): Boolean {
        if (CMD_DEAL == action) {
            if (CMD_DEAL != ra) {
                dealerMessage = String.format("%s.", msg.handActiveMsg)
                return false
            }
        } else {
            if (CMD_DEAL == ra) {
                dealerMessage = String.format("%s L=%s", msg.pleaseSelectMsg, msg.dealMsg)
                return false
            }
        }
        return true
    }

    private fun updateMessages(
        recommendedActionHeader: String,
        recommendedActionStrategyLine: String
    ) {
        if (Stringop.isPopulated(recommendedActionStrategyLine)) {
            mistakeHeader = recommendedActionHeader
            mistakeMessage = recommendedActionStrategyLine
            metrics.incrementMistakeCount()
        }
    }

    private val isShuffleNeeded: Boolean
        get() = BlackjackShoe.remaining() < BlackjackShoe.cut()

    fun setNextBet(value: Int) {
        nextBet = value
    }

    private val wagerIncrement: Int
        get() = config.betIncrementInWholeDollars * 100

    private fun increase() {
        setNextBet(getNextBet() + wagerIncrement)
        updateInactiveHands()
    }

    private fun decrease() {
        setNextBet(getNextBet() - wagerIncrement)
        updateInactiveHands()
    }

    private fun updateInactiveHands() {
        for (player in getCycle().players) {
            for (handPlayer in player.hands) {
                if (!handPlayer.hasCards()) {
                    bankroll.subtract(-handPlayer.wager.initialBet)
                    handPlayer.wager.initialBet = getNextBet()
                    bankroll.subtract(handPlayer.wager.initialBet)
                }
            }
        }
    }

    fun getCycle(): Cycle {
        return ct.getCycle(this, strategy)
    }

    fun isAutomaticRunning(): Boolean {
        return automatic.name.equals(automaticRunningName)
    }

    private fun auto() {
        if (isAutomaticRunning()) {
            stopAutomaticThread()
        } else {
            if (isReadyToRunAutomatically()) {
                startAutomaticThread()
            }
        }
    }

    private fun stopAutomaticThread() {
        automatic.name = "PanStopRequested"
    }

    private var automatic = createNewAutomaticThread()

    private fun createNewAutomaticThread(): Thread {
        return Thread {
            doAutomatic()
        }
    }

    private val automaticRunningName = "PanAutomatic"

    private fun startAutomaticThread() {
        if (automaticRunningName == automatic.name) {
            return
        }
        automatic = createNewAutomaticThread()
        automatic.name = automaticRunningName
        automatic.start()
    }

    private fun doAutomatic() {
        while (isAutomaticRunning() && Zombie.isActive) {
            val ra: String = getCycle().getRecommendedAction()
            if (CMD_DEAL == ra) {
                val targetStake: Int = config.targetStakeInWholeDollars * 100
                if (targetStake > 0 && bankroll.getStakeOutOfPlay() >= targetStake) {
                    Logz.green(
                        "Target stake of ${Stringop.getDollarString(targetStake.toLong())} " +
                                "reached, automatic execution ended."
                    )
                    stopAutomaticThread()
                    return
                }
            }
            exec(ra)
        }
    }

    private fun isReadyToRunAutomatically(): Boolean {
        if (isAutomaticRunning()) {
            return false
        }
        if (config.isBasicStrategyVariationsOnly) {
            val message =
                "Please turn off \"Variations\" in the Configuration->Rules tab before running automatically."
            Logz.warn(message)
            dealerMessage = message
            return false
        }
        return true
    }

    fun shuffle() {
        BlackjackShoe.shuffle()
        countingSystems.resetCount()
        dealerMessage = if (BlackjackShoe.isTheDeckStacked()) {
            "Shuffled and stacked deck for debugging"
        } else {
            msg.shuffledMsg
        }
    }

    private var nextBet = 0
    private val minBet: Int
        get() = 100 * config.minimumBetInWholeDollars

    fun getNextBet(): Int {
        if (nextBet == 0) {
            nextBet = minBet
        }
        return nextBet
    }

    private fun resetNextBet() {
        nextBet = minBet
    }

    var mistakeMessage: String = ""
    var mistakeHeader: String = ""
    var dealerMessage: String = ""

    private fun clearMessages() {
        mistakeHeader = ""
        mistakeMessage = ""
        dealerMessage = ""
    }

    fun getGameStatusVertical(): List<String> {
        val rtn: MutableList<String> = ArrayList()
        val st = StringTokenizer(getGameStatus(), "|")
        while (st.hasMoreElements()) {
            rtn.add(st.nextToken().trim { it <= ' ' })
        }
        return rtn
    }

    fun getGameStatusHorizontal(): String {
        return getGameStatus().replace("|", "")
    }

    fun getStatusChipsVertical(): List<String> {
            val rtn: MutableList<String> = ArrayList()
            val st = StringTokenizer(getRawStatusChips(), "|")
            while (st.hasMoreElements()) {
                rtn.add(st.nextToken().trim { it <= ' ' })
            }
            return rtn
        }

    fun getStatusChipsHorizontal(): String {
        return getRawStatusChips().replace("|", "")
    }

    fun getGameStatus(): String {
        val sw = StringWriter()
        sw.append("| Stake: ${Stringop.getDollarString(bankroll.getStakeIncludingHands(getCycle().players))}")
        sw.append("| Chips: ${Stringop.getDollarString(bankroll.getChips())}")
        sw.append("| Score: " + metrics.handsSinceLastMistake)
        sw.append(" (" + metrics.getHandsSinceLastMistakeRecord() + ")")
        if (config.isShowCount) {
            sw.append("| ")
            sw.append(countingSystems.findSelected().name)
            sw.append(": " + countingSystems.findSelected().count)
        }
        return sw.toString()
    }
    fun getRawStatusChips(): String {
            val sw = StringWriter()
            sw.append("  reloads: " + bankroll.reloadCount)
            sw.append("|  Chips: " + Stringop.getDollarString(bankroll.getChips()))
            sw.append("|  Next bet: " + Stringop.getDollarString(getNextBet().toLong()))
            return sw.toString()
    }

    fun reportNewHand() {
        if (isAutomaticRunning()) {
            metrics.reportNewHandAutomatic()
        } else {
            metrics.reportNewHand()
        }
    }

    var isShuffleFlagOn = false
        private set

    fun triggerShuffleBeforeNextHand() {
        isShuffleFlagOn = true
    }

    var isBustedPriorHand = false
        private set

    fun setPriorHandBustedFlag() {
        isBustedPriorHand = true
    }

    fun clearPriorHandBustedFlag() {
        isBustedPriorHand = false
    }

    var streak = 0
        private set

    fun incrementStreak() {
        streak++
    }

    fun resetStreak() {
        streak = 0
    }

    fun getTotalValue(): Long {
        return bankroll.getStakeIncludingHands(ct.cycle?.players)
    }

    fun setAutomaticOnForTesting() {
        startAutomaticThread()
    }

    fun clearAutomaticForTesting() {
        stopAutomaticThread()
    }

    fun waitMillis(i: Long) {
        Thread.sleep(i)
    }

    fun isCountVeryNegative(): Boolean {
        return countingSystems.getTrueCount() < config.strategicVeryNegativeCount
    }

    fun isCountVeryPositive(): Boolean {
        return countingSystems.getTrueCount() > config.strategicVeryPositiveCount
    }
    fun getChipsIncludingHands(): Long {
      return bankroll.getChips() + bankroll.getLiveValue(getCycle().players)
    }

    fun getCurrentSnapshot(): CycleSnapshot {
        return CycleSnapshot(cloneState(""))
    }

    private fun getNewActionCycleSnapshot(action: String): CycleSnapshot? {
        lastActionSnapshot = CycleSnapshot(cloneState(action))
        return lastActionSnapshot
    }

    var lastActionSnapshot: CycleSnapshot? = null
}
