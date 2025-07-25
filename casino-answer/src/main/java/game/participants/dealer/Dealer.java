package game.participants.dealer;

import game.components.deck.Deck;
import game.components.hand.Hand;
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
        // 새로운 덱 생성
        deck = new Deck();
        deck.shuffle();
    }
    
    /**
     * 플레이어들에게 카드를 분배합니다.
     * 
     * @param players 카드를 받을 플레이어 목록
     */
    public void dealCards(List<? extends Player> players) {
        // 모든 플레이어의 핸드를 초기화
        for (Player player : players) {
            player.setHand(new Hand());
        }
        
        // 각 플레이어에게 5장씩 분배
        for (int i = 0; i < CARDS_PER_PLAYER; i++) {
            for (Player player : players) {
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
        List<Player> winners = new ArrayList<>();
        int highestScore = 0;
        
        // 최고 점수 찾기
        for (Player player : players) {
            int score = player.getHand().open();
            if (score > highestScore) {
                highestScore = score;
            }
        }
        
        // 최고 점수를 가진 모든 플레이어 찾기
        for (Player player : players) {
            if (player.getHand().open() == highestScore) {
                winners.add(player);
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
        for (Player winner : winners) {
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
        
        for (int round = 1; round <= rounds; round++) {
            System.out.println("\n=== 라운드 " + round + " ===");
            
            // 새 게임 시작
            startNewGame();
            
            // 카드 분배
            dealCards(players);
            
            // 각 플레이어의 핸드 출력
            System.out.println("플레이어 핸드:");
            for (Player player : players) {
                System.out.println(player.getName() + ": " + player.getHand() + 
                    " (" + player.getHand().evaluate() + ")");
            }
            
            // 승자 판정
            List<? extends Player> winners = determineWinners(players);
            
            // 결과 출력 및 기록 업데이트
            if (winners.size() == players.size()) {
                // 모든 플레이어가 동점 - 무승부
                System.out.println("\n결과: 무승부!");
                System.out.println("상금: 없음");
                for (Player player : players) {
                    player.recordDraw();
                }
                // 무승부 시에는 상금 분배 없음
            } else {
                // 승자 출력
                System.out.println("\n승자:");
                for (Player winner : winners) {
                    System.out.println("  🏆 " + winner.getName() + " - " + 
                        winner.getHand().evaluate() + " (+" + PRIZE_PER_ROUND + "원)");
                }
                
                // 승자와 패자 기록
                for (Player player : players) {
                    if (winners.contains(player)) {
                        player.recordWin();
                    } else {
                        player.recordLose();
                    }
                }
                // 승자에게만 상금 분배
                distributePrize(winners, PRIZE_PER_ROUND);
            }
        }
    }
}