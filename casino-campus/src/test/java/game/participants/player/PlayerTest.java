package game.participants.player;

import game.components.hand.Hand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Player 클래스 테스트
 * 
 * 플레이어의 기본 기능을 검증합니다.
 */
public class PlayerTest {
    
    private Player player;
    
    @BeforeEach
    void setUp() {
        player = new Player("테스트플레이어", 10000);
    }
    
    @Test
    @DisplayName("1. 플레이어 생성 테스트")
    void testPlayerCreation() {
        // then
        assertEquals("테스트플레이어", player.getName());
        assertEquals(10000, player.getMoney());
        assertNotNull(player.getHand());
        assertEquals(0, player.getWinCount());
        assertEquals(0, player.getLoseCount());
        assertEquals(0, player.getDrawCount());
    }
    
    @Test
    @DisplayName("2. 플레이어 이름이 null일 때 예외 발생")
    void testPlayerCreationWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> new Player(null, 10000));
    }
    
    @Test
    @DisplayName("3. 플레이어 이름이 빈 문자열일 때 예외 발생")
    void testPlayerCreationWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> new Player("", 10000));
        assertThrows(IllegalArgumentException.class, () -> new Player("   ", 10000));
    }
    
    @Test
    @DisplayName("4. 초기 자금이 음수일 때 예외 발생")
    void testPlayerCreationWithNegativeMoney() {
        assertThrows(IllegalArgumentException.class, () -> new Player("테스트", -1000));
    }
    
    @Test
    @DisplayName("5. 돈 추가 테스트")
    void testAddMoney() {
        // when
        player.addMoney(500);
        
        // then
        assertEquals(10500, player.getMoney());
    }
    
    @Test
    @DisplayName("6. 음수 금액 추가 시 예외 발생")
    void testAddNegativeMoney() {
        assertThrows(IllegalArgumentException.class, () -> player.addMoney(-100));
        assertEquals(10000, player.getMoney()); // 금액이 변하지 않아야 함
    }
    
    @Test
    @DisplayName("7. 돈 차감 성공 테스트")
    void testRemoveMoneySuccess() {
        // when
        boolean result = player.removeMoney(3000);
        
        // then
        assertTrue(result);
        assertEquals(7000, player.getMoney());
    }
    
    @Test
    @DisplayName("8. 잔액 부족 시 돈 차감 실패")
    void testRemoveMoneyInsufficientFunds() {
        // when
        boolean result = player.removeMoney(15000);
        
        // then
        assertFalse(result);
        assertEquals(10000, player.getMoney()); // 금액이 변하지 않아야 함
    }
    
    @Test
    @DisplayName("9. 음수 금액 차감 시 실패")
    void testRemoveNegativeMoney() {
        // when
        boolean result = player.removeMoney(-500);
        
        // then
        assertFalse(result);
        assertEquals(10000, player.getMoney());
    }
    
    @Test
    @DisplayName("10. 정확한 잔액 차감 테스트")
    void testRemoveExactAmount() {
        // when
        boolean result = player.removeMoney(10000);
        
        // then
        assertTrue(result);
        assertEquals(0, player.getMoney());
    }
    
    @Test
    @DisplayName("11. 핸드 설정 테스트")
    void testSetHand() {
        // given
        Hand newHand = new Hand();
        
        // when
        player.setHand(newHand);
        
        // then
        assertSame(newHand, player.getHand());
    }
    
    @Test
    @DisplayName("12. null 핸드 설정 시 예외 발생")
    void testSetNullHand() {
        assertThrows(IllegalArgumentException.class, () -> player.setHand(null));
    }
    
    @Test
    @DisplayName("13. 승리 기록 테스트")
    void testRecordWin() {
        // when
        player.recordWin();
        player.recordWin();
        player.recordWin();
        
        // then
        assertEquals(3, player.getWinCount());
        assertEquals(0, player.getLoseCount());
        assertEquals(0, player.getDrawCount());
    }
    
    @Test
    @DisplayName("14. 패배 기록 테스트")
    void testRecordLose() {
        // when
        player.recordLose();
        player.recordLose();
        
        // then
        assertEquals(0, player.getWinCount());
        assertEquals(2, player.getLoseCount());
        assertEquals(0, player.getDrawCount());
    }
    
    @Test
    @DisplayName("15. 무승부 기록 테스트")
    void testRecordDraw() {
        // when
        player.recordDraw();
        
        // then
        assertEquals(0, player.getWinCount());
        assertEquals(0, player.getLoseCount());
        assertEquals(1, player.getDrawCount());
    }
    
    @Test
    @DisplayName("16. 전적 통합 테스트")
    void testRecordMixed() {
        // when
        player.recordWin();
        player.recordWin();
        player.recordLose();
        player.recordDraw();
        player.recordWin();
        player.recordLose();
        
        // then
        assertEquals(3, player.getWinCount());
        assertEquals(2, player.getLoseCount());
        assertEquals(1, player.getDrawCount());
    }
    
    @Test
    @DisplayName("17. toString() 테스트")
    void testToString() {
        // given
        player.recordWin();
        player.recordWin();
        player.recordLose();
        player.recordDraw();
        player.addMoney(500);
        
        // when
        String result = player.toString();
        
        // then
        assertTrue(result.contains("테스트플레이어"));
        assertTrue(result.contains("10500원"));
        assertTrue(result.contains("2승"));
        assertTrue(result.contains("1패"));
        assertTrue(result.contains("1무"));
    }
    
    @Test
    @DisplayName("18. 연속 자금 거래 테스트")
    void testContinuousMoneyTransactions() {
        // when & then
        player.addMoney(1000);
        assertEquals(11000, player.getMoney());
        
        assertTrue(player.removeMoney(500));
        assertEquals(10500, player.getMoney());
        
        player.addMoney(2000);
        assertEquals(12500, player.getMoney());
        
        assertTrue(player.removeMoney(12500));
        assertEquals(0, player.getMoney());
        
        assertFalse(player.removeMoney(1));
        assertEquals(0, player.getMoney());
    }
}