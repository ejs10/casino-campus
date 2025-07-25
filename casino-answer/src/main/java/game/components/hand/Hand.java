package game.components.hand;

import game.components.card.Card;
import game.components.card.Rank;
import game.components.card.Suit;

import java.util.*;

/**
 * 플레이어의 손패를 나타내는 클래스
 * 
 * 이 클래스는 카드 게임에서 플레이어가 들고 있는 카드들(손패)의 관리 기능을 정의합니다.
 * 손패는 가변적이며, 게임 진행에 따라 카드를 추가하거나 제거할 수 있습니다.
 * 
 * <p>구현 요구사항:</p>
 * <ul>
 *   <li>카드를 추가할 수 있어야 합니다</li>
 *   <li>전체 패를 버릴 수 있어야 합니다 (clear)</li>
 *   <li>카드 수에 제한이 없어야 합니다 (게임 규칙은 별도로 처리)</li>
 *   <li>카드 리스트의 순서를 유지해야 합니다</li>
 *   <li>getCards()는 방어적 복사본을 반환해야 합니다</li>
 *   <li>null 카드는 허용하지 않아야 합니다</li>
 * </ul>
 * 
 * <p>사용 예시:</p>
 * <pre>
 * Hand hand = new Hand();
 * hand.add(card1);
 * hand.add(card2);
 * hand.clear();  // 패를 버리고 새로 시작
 * hand.add(card3);
 * </pre>
 * 
 * 구현이 필요한 메서드:
 * - evaluate() 메서드: 포커 족보 판정
 * - open() 메서드: 패를 공개하고 점수 반환
 * - compareTo() 메서드: 핸드 비교
 * - 각종 족보 판정 헬퍼 메서드들
 * 
 * @author XIYO
 * @version 1.0
 * @since 2024-01-01
 */
public class Hand implements Comparable<Hand> {
    private List<Card> cards  = new ArrayList<>();
    private static final int MAX_CARDS = 5;
    
    /**
     * 손패에 카드를 추가합니다.
     * 
     * 카드는 손패의 끝에 추가됩니다.
     * 
     * @param card 추가할 카드
     * @throws IllegalArgumentException card가 null일 때
     */
    public void add(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("카드는 null일 수 없습니다.");
        }
        if (isFull()) {
            throw new IllegalStateException("핸드는 최대 " + MAX_CARDS + "장까지만 가질 수 있습니다.");
        }
        cards.add(card);
    }
    
    /**
     * 손패에 있는 모든 카드를 반환합니다.
     * 
     * 반환되는 리스트는 수정할 수 없는 읽기 전용 리스트입니다.
     * 원본 손패를 보호하기 위해 변경이 불가능한 리스트를 반환합니다.
     * 
     * @return 수정 불가능한 카드 리스트 (빈 손패일 경우 빈 리스트)
     */
    public List<Card> getCards() {
        return List.copyOf(cards);
    }
    
    /**
     * 손패가 가득 찼는지 확인합니다.
     * 
     * @return 카드가 5장이면 true, 아니면 false
     */
    public boolean isFull() {
        return cards.size() == MAX_CARDS;
    }
    
    
    /**
     * 손패를 정리합니다.
     * 
     * 현재 들고 있는 모든 카드를 버리고 빈 손이 됩니다.
     * 새로운 게임을 시작하거나 패를 교체할 때 사용합니다.
     */
    public void clear() {
        cards.clear();
    }
    
    
    /**
     * 손패를 문자열로 표현합니다.
     * 
     * 형식: "[카드1, 카드2, ..., 카드N]"
     * 빈 손패의 경우: "[]"
     * 
     * @return 손패의 문자열 표현
     */
    @Override
    public String toString() {
        return cards.toString();
    }
    
    /**
     * 손패의 포커 순위를 평가합니다.
     * 
     * 5장의 카드로 이루어진 손패를 평가하여 포커 족보를 반환합니다.
     * 카드가 5장이 아닌 경우 예외를 발생시킵니다.
     * 
     * @return 평가된 포커 족보
     * @throws IllegalStateException 카드가 정확히 5장이 아닐 때
     */
    public HandRank evaluate() {
        if (cards.size() != 5) {
            throw new IllegalStateException("핸드는 정확히 5장이어야 평가할 수 있습니다.");
        }
        
        // 높은 족보부터 차례대로 확인
        if (isRoyalFlush()) return HandRank.ROYAL_FLUSH;
        if (isStraightFlush()) return HandRank.STRAIGHT_FLUSH;
        if (isFourOfAKind()) return HandRank.FOUR_OF_A_KIND;
        if (isFullHouse()) return HandRank.FULL_HOUSE;
        if (isFlush()) return HandRank.FLUSH;
        if (isStraight()) return HandRank.STRAIGHT;
        if (isThreeOfAKind()) return HandRank.THREE_OF_A_KIND;
        if (isTwoPair()) return HandRank.TWO_PAIR;
        if (isOnePair()) return HandRank.ONE_PAIR;
        
        return HandRank.HIGH_CARD;
    }
    
    /**
     * 손패를 공개하고 포커 점수를 반환합니다.
     * 
     * 현재 손패의 포커 족보를 평가하고 그에 해당하는 점수를 반환합니다.
     * 카드가 5장이 아닌 경우 예외를 발생시킵니다.
     * 
     * @return 포커 족보의 점수 (높을수록 강한 패)
     * @throws IllegalStateException 카드가 정확히 5장이 아닐 때
     */
    public int open() {
        return evaluate().getScore();
    }
    
    public int compareTo(Hand other) {
        return Integer.compare(this.open(), other.open());
    }
    
    // ===== 헬퍼 메서드들 =====
    
    /**
     * 로열 플러시인지 확인
     * @return 로열 플러시이면 true
     */
    private boolean isRoyalFlush() {
        // TODO: 구현하세요 - 학생이 직접 구현해야 하는 메서드
        // 
        // 🌟 구현 힌트:
        // 로열 플러시 = 같은 무늬의 10, J, Q, K, A
        // 
        // 구현 순서:
        // 1. if (!isFlush()) return false;  // 플러시가 아니면 로열 플러시도 아님
        // 2. 필요한 랭크들을 Set에 저장
        //    Set<Rank> requiredRanks = new HashSet<>();
        //    requiredRanks.add(Rank.TEN);
        //    requiredRanks.add(Rank.JACK);
        //    requiredRanks.add(Rank.QUEEN);
        //    requiredRanks.add(Rank.KING);
        //    requiredRanks.add(Rank.ACE);
        // 3. 현재 카드들의 랭크를 Set에 저장
        //    Set<Rank> currentRanks = new HashSet<>();
        //    for (Card card : cards) {
        //        currentRanks.add(card.getRank());
        //    }
        // 4. return currentRanks.equals(requiredRanks);
        // 
        // 필요한 import:
        // import java.util.Set;
        // import java.util.HashSet;
        // 
        // 테스트 실패 시:
        // HandTest.java의 "15. 로열 플러시 판정 테스트"가 실패합니다.
        // 이 메서드를 구현해야 테스트가 통과합니다.
        
        if (!isFlush()) return false;
        
        Set<Rank> requiredRanks = new HashSet<>();
        requiredRanks.add(Rank.TEN);
        requiredRanks.add(Rank.JACK);
        requiredRanks.add(Rank.QUEEN);
        requiredRanks.add(Rank.KING);
        requiredRanks.add(Rank.ACE);
        
        Set<Rank> currentRanks = new HashSet<>();
        for (Card card : cards) {
            currentRanks.add(card.getRank());
        }
        
        return currentRanks.equals(requiredRanks);
    }
    
    /**
     * 스트레이트 플러시인지 확인
     * @return 스트레이트 플러시이면 true
     */
    private boolean isStraightFlush() {
        // TODO: 구현하세요 - 학생이 직접 구현해야 하는 메서드
        // 
        // ✨ 구현 힌트:
        // 스트레이트 플러시 = 플러시 + 스트레이트
        // 
        // 한 줄로 구현 가능:
        // return isFlush() && isStraight();
        // 
        // 테스트 실패 시:
        // HandTest.java의 "16. 스트레이트 플러시 판정 테스트"가 실패합니다.
        // 이 메서드를 구현해야 테스트가 통과합니다.
        
        return isFlush() && isStraight();
    }
    
    /**
     * 포카드인지 확인
     * @return 포카드이면 true
     */
    private boolean isFourOfAKind() {
        Map<Rank, Integer> counts = getRankCounts();
        return counts.containsValue(4);
    }
    
    /**
     * 풀하우스인지 확인
     * @return 풀하우스이면 true
     */
    private boolean isFullHouse() {
        // TODO: 구현하세요 - 학생이 직접 구현해야 하는 메서드
        // 
        // 🏠 구현 힌트:
        // 풀하우스 = 3장 + 2장 조합
        // 
        // 예시:
        // Map<Rank, Integer> counts = getRankCounts();
        // return counts.containsValue(3) && counts.containsValue(2);
        // 
        // getRankCounts()는 이미 구현되어 있습니다!
        // 
        // 테스트 실패 시:
        // HandTest.java의 "17. 풀하우스 판정 테스트"가 실패합니다.
        // 이 메서드를 구현해야 테스트가 통과합니다.
        
        Map<Rank, Integer> counts = getRankCounts();
        return counts.containsValue(3) && counts.containsValue(2);
    }
    
    /**
     * 플러시인지 확인
     * @return 플러시이면 true
     */
    private boolean isFlush() {
        Suit firstSuit = cards.get(0).getSuit();
        for (Card card : cards) {
            if (card.getSuit() != firstSuit) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 스트레이트인지 확인
     * @return 스트레이트이면 true
     */
    private boolean isStraight() {
        // TODO: 구현하세요 - 학생이 직접 구현해야 하는 메서드
        // 
        // 📏 구현 힌트:
        // 스트레이트 = 연속된 5장의 카드
        // 
        // 구현 순서:
        // 1. 카드들의 값을 List에 저장하고 정렬
        //    List<Integer> values = new ArrayList<>();
        //    for (Card card : cards) {
        //        values.add(card.getValue());
        //    }
        //    Collections.sort(values);
        // 
        // 2. 일반 스트레이트 체크 (연속된 5개 숫자)
        //    boolean isNormalStraight = true;
        //    for (int i = 0; i < 4; i++) {
        //        if (values.get(i + 1) - values.get(i) != 1) {
        //            isNormalStraight = false;
        //            break;
        //        }
        //    }
        // 
        // 3. 특수 케이스: A-2-3-4-5 (백스트레이트)
        //    - 정렬된 값이 [2, 3, 4, 5, 14]인지 확인
        //    - 14는 ACE의 값
        //    boolean isAceLowStraight = values.equals(Arrays.asList(2, 3, 4, 5, 14));
        // 
        // 4. return isNormalStraight || isAceLowStraight;
        // 
        // 필요한 import:
        // import java.util.Arrays;
        // import java.util.Collections;
        // 
        // 테스트 실패 시:
        // HandTest.java의 "18. 스트레이트 판정 테스트"가 실패합니다.
        // HandTest.java의 "19. 백스트레이트(A-2-3-4-5) 판정 테스트"도 확인하세요.
        // 이 메서드를 구현해야 테스트가 통과합니다.
        
        List<Integer> values = new ArrayList<>();
        for (Card card : cards) {
            values.add(card.getValue());
        }
        Collections.sort(values);
        
        // 일반 스트레이트 체크
        boolean isNormalStraight = true;
        for (int i = 0; i < 4; i++) {
            if (values.get(i + 1) - values.get(i) != 1) {
                isNormalStraight = false;
                break;
            }
        }
        
        // 특수 케이스: A-2-3-4-5 (백스트레이트)
        boolean isAceLowStraight = values.equals(Arrays.asList(2, 3, 4, 5, 14));
        
        return isNormalStraight || isAceLowStraight;
    }
    
    /**
     * 쓰리카드인지 확인
     * @return 쓰리카드이면 true
     */
    private boolean isThreeOfAKind() {
        // TODO: 구현하세요 - 학생이 직접 구현해야 하는 메서드
        // 
        // 🎯 구현 힌트:
        // 쓰리카드 = 같은 랭크 3장
        // 
        // 한 줄로 구현 가능:
        // Map<Rank, Integer> counts = getRankCounts();
        // return counts.containsValue(3);
        // 
        // 테스트 실패 시:
        // HandTest.java의 "20. 쓰리카드 판정 테스트"가 실패합니다.
        // 이 메서드를 구현해야 테스트가 통과합니다.
        
        Map<Rank, Integer> counts = getRankCounts();
        return counts.containsValue(3);
    }
    
    /**
     * 투페어인지 확인
     * @return 투페어이면 true
     */
    private boolean isTwoPair() {
        // TODO: 구현하세요 - 학생이 직접 구현해야 하는 메서드
        // 
        // 👥 구현 힌트:
        // 투페어 = 페어가 2개
        // 
        // 예시:
        // Map<Rank, Integer> counts = getRankCounts();
        // int pairCount = 0;
        // for (int count : counts.values()) {
        //     if (count == 2) {
        //         pairCount++;
        //     }
        // }
        // return pairCount == 2;
        // 
        // 테스트 실패 시:
        // HandTest.java의 "21. 투페어 판정 테스트"가 실패합니다.
        // 이 메서드를 구현해야 테스트가 통과합니다.
        
        Map<Rank, Integer> counts = getRankCounts();
        int pairCount = 0;
        for (int count : counts.values()) {
            if (count == 2) {
                pairCount++;
            }
        }
        return pairCount == 2;
    }
    
    /**
     * 원페어인지 확인
     * @return 원페어이면 true
     */
    private boolean isOnePair() {
        Map<Rank, Integer> counts = getRankCounts();
        return counts.containsValue(2);
    }
    
    /**
     * 각 랭크별 카드 개수를 계산
     * @return 랭크별 카드 개수 맵
     */
    private Map<Rank, Integer> getRankCounts() {
        Map<Rank, Integer> counts = new EnumMap<>(Rank.class);
        for (Card card : cards) {
            counts.put(card.getRank(), counts.getOrDefault(card.getRank(), 0) + 1);
        }
        return counts;
    }
}