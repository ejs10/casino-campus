package game.participants.dealer;

import game.components.deck.Deck;
import game.participants.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * 딜러의 기본 동작을 정의하는 클래스입니다.
 * 딜러는 게임을 진행하고, 카드를 나누고, 승자를 결정하는 역할을 합니다.
 * 
 * 구현이 필요한 메서드:
 * - startNewGame() 메서드: 새 게임 시작
 * - dealCards() 메서드: 카드 분배
 * - determineWinners() 메서드: 승자 판정
 * - distributePrize() 메서드: 상금 분배
 * - playGame() 메서드: 전체 게임 진행
 * 
 * @author XIYO
 * @version 1.0
 * @since 2024-01-01
 */
public class Dealer {
    private Deck deck;
    private static final int CARDS_PER_PLAYER = 5;
    private static final int PRIZE_PER_ROUND = 100;
    
    /**
     * Dealer 생성자
     */
    public Dealer() {
        this.deck = new Deck();
    }
    
    /**
     * 새로운 게임을 시작합니다.
     * 덱을 초기화하고 셔플합니다.
     */
    public void startNewGame() {
        // TODO: 구현하세요
        // 힌트:
        // 1. deck.reset()으로 덱 초기화
        // 2. deck.shuffle()로 카드 섞기


        this.deck = new Deck();

        deck.shuffle();


    }
    
    /**
     * 플레이어들에게 카드를 분배합니다.
     * 
     * @param players 카드를 받을 플레이어 목록
     */
    public void dealCards(List<? extends Player> players) {
        // TODO: 구현하세요
        // 힌트:
        // 1. 각 플레이어의 핸드를 clear()
        // 2. 각 플레이어에게 CARDS_PER_PLAYER장씩 분배
        // 3. deck.drawCard()를 사용하여 카드를 뽑고
        // 4. player.getHand().addCard()로 추가

        for(Player player : players) {
            player.getHand().clear();
            for (int i = 0; i < CARDS_PER_PLAYER; i++) {
                player.getHand().add(deck.drawCard());
            }
        }
    }
    
    /**
     * 라운드의 승자를 결정합니다.
     * 
     * @param players 참가 플레이어 목록
     * @return 승자 목록 (동점일 경우 여러 명)
     */
    public List<? extends Player> determineWinners(List<? extends Player> players) {
        // TODO: 구현하세요
        // 힌트:
        // 1. 최고 점수를 가진 플레이어들을 찾기
        // 2. List<Player> winners = new ArrayList<>();
        // 3. 각 플레이어의 hand.evaluateHand()로 핸드 순위 확인
        // 4. 가장 높은 점수를 가진 플레이어들을 winners에 추가
        // 5. 동점자가 있을 수 있음을 고려

        List<Player> winners = new ArrayList<>();
        int highestScore = Integer.MIN_VALUE;

        for(Player player : players){
            int score = player.getHand().evaluate().getScore();
            if (score > highestScore) {
                winners.clear();
                winners.add(player);
                highestScore = score;

            } else if (score == highestScore) {
                winners.add(player); // 동점이면 승자 목록에 추가
            }
        }
        return winners;
    }
    
    /**
     * 승자들에게 상금을 분배합니다.
     * 
     * @param winners 승자 목록
     * @param prizeAmount 각 승자가 받을 상금
     */
    public void distributePrize(List<? extends Player> winners, int prizeAmount) {
        // TODO: 구현하세요
        // 힌트:
        // 1. 각 승자에게 prizeAmount만큼 돈 추가
        // 2. winner.addMoney(prizeAmount) 사용
        for (Player winner : winners){
            winner.addMoney(prizeAmount);
        }

    }
    
    /**
     * 전체 게임을 진행합니다.
     * 
     * @param players 참가 플레이어 목록
     * @param rounds 진행할 라운드 수
     */
    public void playGame(List<? extends Player> players, int rounds) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("플레이어가 없습니다.");
        }
        if (rounds <= 0) {
            throw new IllegalArgumentException("라운드 수는 양수여야 합니다.");
        }
        
        // TODO: 구현하세요
        // 힌트:
        // for (int round = 1; round <= rounds; round++) {
        //     1. startNewGame()으로 새 게임 시작
        //     2. dealCards(players)로 카드 분배
        //     3. List<? extends Player> winners = determineWinners(players)
        //     4. distributePrize(winners, PRIZE_PER_ROUND)
        //     5. 기록 업데이트:
        //        - 승자들: winner.recordWin()
        //        - 패자들: loser.recordLose()
        //        - 동점자가 여러명이면: 모두 recordDraw()
        // }

        for (int round = 1; round <= round; round++){
            startNewGame();
            dealCards(players);
            List<? extends Player> winners = determineWinners(players);
            distributePrize(winners, PRIZE_PER_ROUND);

            if(winners.size() == 1){
                Player winner = winners.get(0);
                winner.recordWin();
                for (Player player : players){
                    if (!winners.contains(player)){
                        player.recordLose();
                    }
                }
            }else {
                for (Player player : players){
                    if(winners.contains(player)){
                        player.recordDraw();
                    }else {
                        player.recordLose();
                    }
                }
            }
        }
    }
}