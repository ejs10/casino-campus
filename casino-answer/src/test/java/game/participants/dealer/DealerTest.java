package game.participants.dealer;

import game.components.card.Card;
import game.components.card.Rank;
import game.components.card.Suit;
import game.components.hand.Hand;
import game.participants.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Dealer 클래스 테스트
 * 
 * 딜러의 게임 진행 기능을 검증합니다.
 */
public class DealerTest {
    
    private Dealer dealer;
    private List<Player> players;
    
    @BeforeEach
    void setUp() {
        dealer = new Dealer();
        players = new ArrayList<>();
        players.add(new Player("플레이어1", 10000));
        players.add(new Player("플레이어2", 10000));
        players.add(new Player("플레이어3", 10000));
        players.add(new Player("플레이어4", 10000));
    }
    
    @Test
    @DisplayName("1. 딜러 생성 테스트")
    void testDealerCreation() {
        assertNotNull(dealer);
    }
    
    @Test
    @DisplayName("2. 새 게임 시작 테스트")
    void testStartNewGame() {
        // when
        dealer.startNewGame();
        
        // then - 예외가 발생하지 않으면 성공
        assertDoesNotThrow(() -> dealer.dealCards(players));
    }
    
    @Test
    @DisplayName("3. 카드 분배 테스트")
    void testDealCards() {
        // given
        dealer.startNewGame();
        
        // when
        dealer.dealCards(players);
        
        // then
        for (Player player : players) {
            assertNotNull(player.getHand());
            assertEquals(5, player.getHand().getCards().size());
            assertTrue(player.getHand().isFull());
        }
    }
    
    @Test
    @DisplayName("4. 카드 중복 분배 방지 테스트")
    void testNoDuplicateCards() {
        // given
        dealer.startNewGame();
        dealer.dealCards(players);
        
        // when - 모든 카드 수집
        List<Card> allCards = new ArrayList<>();
        for (Player player : players) {
            allCards.addAll(player.getHand().getCards());
        }
        
        // then - 중복 카드가 없어야 함
        assertEquals(20, allCards.size()); // 4명 * 5장 = 20장
        for (int i = 0; i < allCards.size(); i++) {
            for (int j = i + 1; j < allCards.size(); j++) {
                assertNotEquals(allCards.get(i), allCards.get(j),
                    "중복된 카드가 발견되었습니다: " + allCards.get(i));
            }
        }
    }
    
    @Test
    @DisplayName("5. 승자 판정 테스트 - 단독 승자")
    void testDetermineWinnersSingle() {
        // given - 수동으로 핸드 설정
        Hand royalFlush = createRoyalFlushHand();
        Hand straightFlush = createStraightFlushHand();
        Hand fourOfAKind = createFourOfAKindHand();
        Hand fullHouse = createFullHouseHand();
        
        players.get(0).setHand(royalFlush);
        players.get(1).setHand(straightFlush);
        players.get(2).setHand(fourOfAKind);
        players.get(3).setHand(fullHouse);
        
        // when
        List<? extends Player> winners = dealer.determineWinners(players);
        
        // then
        assertEquals(1, winners.size());
        assertEquals("플레이어1", winners.get(0).getName());
    }
    
    @Test
    @DisplayName("6. 승자 판정 테스트 - 동점자 여러명")
    void testDetermineWinnersMultiple() {
        // given - 두 명이 같은 패
        Hand flush1 = createFlushHand();
        Hand flush2 = createFlushHand();
        Hand straight = createStraightHand();
        Hand pair = createOnePairHand();
        
        players.get(0).setHand(flush1);
        players.get(1).setHand(flush2);
        players.get(2).setHand(straight);
        players.get(3).setHand(pair);
        
        // when
        List<? extends Player> winners = dealer.determineWinners(players);
        
        // then
        assertEquals(2, winners.size());
        assertTrue(winners.stream().anyMatch(p -> p.getName().equals("플레이어1")));
        assertTrue(winners.stream().anyMatch(p -> p.getName().equals("플레이어2")));
    }
    
    @Test
    @DisplayName("7. 상금 분배 테스트")
    void testDistributePrize() {
        // given
        List<Player> winners = new ArrayList<>();
        winners.add(players.get(0));
        winners.add(players.get(2));
        
        // when
        dealer.distributePrize(winners, 100);
        
        // then
        assertEquals(10100, players.get(0).getMoney());
        assertEquals(10000, players.get(1).getMoney()); // 미당첨
        assertEquals(10100, players.get(2).getMoney());
        assertEquals(10000, players.get(3).getMoney()); // 미당첨
    }
    
    @Test
    @DisplayName("8. 전체 게임 진행 테스트")
    void testPlayGame() {
        // when
        dealer.playGame(players, 10); // 10라운드만 진행
        
        // then
        int totalWins = 0;
        int totalLoses = 0;
        int totalDraws = 0;
        
        for (Player player : players) {
            totalWins += player.getWinCount();
            totalLoses += player.getLoseCount();
            totalDraws += player.getDrawCount();
            
            // 각 플레이어의 총 게임 수는 10이어야 함
            assertEquals(10, player.getWinCount() + player.getLoseCount() + player.getDrawCount());
        }
        
        // 전체 통계 검증
        assertEquals(40, totalWins + totalLoses + totalDraws); // 4명 * 10라운드
    }
    
    @Test
    @DisplayName("9. 플레이어 없이 게임 진행 시 예외")
    void testPlayGameWithNoPlayers() {
        assertThrows(IllegalArgumentException.class, 
            () -> dealer.playGame(new ArrayList<>(), 10));
    }
    
    @Test
    @DisplayName("10. 음수 라운드로 게임 진행 시 예외")
    void testPlayGameWithNegativeRounds() {
        assertThrows(IllegalArgumentException.class, 
            () -> dealer.playGame(players, -1));
    }
    
    @Test
    @DisplayName("11. 0 라운드로 게임 진행 시 예외")
    void testPlayGameWithZeroRounds() {
        assertThrows(IllegalArgumentException.class, 
            () -> dealer.playGame(players, 0));
    }
    
    @Test
    @DisplayName("12. 100라운드 게임 자금 변화 테스트")
    void testPlayGameMoneyChanges() {
        // when
        dealer.playGame(players, 100);
        
        // then
        int totalMoney = 0;
        int totalWins = 0;
        
        for (Player player : players) {
            // 각 플레이어의 자금 확인
            int finalMoney = player.getMoney();
            assertTrue(finalMoney >= 0, 
                player.getName() + "의 자금이 음수가 되었습니다");
            
            // 정확한 상금 계산 확인 (무승부는 상금 없음)
            int expectedMoney = 10000 + (player.getWinCount() * 100);
            assertEquals(expectedMoney, finalMoney,
                player.getName() + "의 최종 자금이 예상과 다릅니다");
            
            totalMoney += finalMoney;
            totalWins += player.getWinCount();
        }
        
        // 전체 자금의 합은 초기 자금(40,000) + 총 승리 횟수 * 상금(100)
        int expectedTotal = 40000 + (totalWins * 100);
        assertEquals(expectedTotal, totalMoney, 
            "전체 자금의 합이 예상과 다릅니다");
    }
    
    // 헬퍼 메서드들 - 특정 패를 만드는 메서드
    
    private Hand createRoyalFlushHand() {
        Hand hand = new Hand();
        hand.add(new Card(Suit.SPADES, Rank.ACE));
        hand.add(new Card(Suit.SPADES, Rank.KING));
        hand.add(new Card(Suit.SPADES, Rank.QUEEN));
        hand.add(new Card(Suit.SPADES, Rank.JACK));
        hand.add(new Card(Suit.SPADES, Rank.TEN));
        return hand;
    }
    
    private Hand createStraightFlushHand() {
        Hand hand = new Hand();
        hand.add(new Card(Suit.HEARTS, Rank.NINE));
        hand.add(new Card(Suit.HEARTS, Rank.EIGHT));
        hand.add(new Card(Suit.HEARTS, Rank.SEVEN));
        hand.add(new Card(Suit.HEARTS, Rank.SIX));
        hand.add(new Card(Suit.HEARTS, Rank.FIVE));
        return hand;
    }
    
    private Hand createFourOfAKindHand() {
        Hand hand = new Hand();
        hand.add(new Card(Suit.HEARTS, Rank.KING));
        hand.add(new Card(Suit.SPADES, Rank.KING));
        hand.add(new Card(Suit.DIAMONDS, Rank.KING));
        hand.add(new Card(Suit.CLUBS, Rank.KING));
        hand.add(new Card(Suit.HEARTS, Rank.THREE));
        return hand;
    }
    
    private Hand createFullHouseHand() {
        Hand hand = new Hand();
        hand.add(new Card(Suit.HEARTS, Rank.JACK));
        hand.add(new Card(Suit.SPADES, Rank.JACK));
        hand.add(new Card(Suit.DIAMONDS, Rank.JACK));
        hand.add(new Card(Suit.CLUBS, Rank.SEVEN));
        hand.add(new Card(Suit.HEARTS, Rank.SEVEN));
        return hand;
    }
    
    private Hand createFlushHand() {
        Hand hand = new Hand();
        hand.add(new Card(Suit.DIAMONDS, Rank.ACE));
        hand.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        hand.add(new Card(Suit.DIAMONDS, Rank.TEN));
        hand.add(new Card(Suit.DIAMONDS, Rank.FIVE));
        hand.add(new Card(Suit.DIAMONDS, Rank.THREE));
        return hand;
    }
    
    private Hand createStraightHand() {
        Hand hand = new Hand();
        hand.add(new Card(Suit.HEARTS, Rank.TEN));
        hand.add(new Card(Suit.SPADES, Rank.NINE));
        hand.add(new Card(Suit.DIAMONDS, Rank.EIGHT));
        hand.add(new Card(Suit.CLUBS, Rank.SEVEN));
        hand.add(new Card(Suit.HEARTS, Rank.SIX));
        return hand;
    }
    
    private Hand createOnePairHand() {
        Hand hand = new Hand();
        hand.add(new Card(Suit.HEARTS, Rank.ACE));
        hand.add(new Card(Suit.SPADES, Rank.ACE));
        hand.add(new Card(Suit.DIAMONDS, Rank.KING));
        hand.add(new Card(Suit.CLUBS, Rank.QUEEN));
        hand.add(new Card(Suit.HEARTS, Rank.JACK));
        return hand;
    }
}