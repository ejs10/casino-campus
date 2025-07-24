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
        // TODO: 구현하세요
        //
        // 구현 힌트:
        // 1. 먼저 플러시인지 확인하세요 (isFlush() 메서드 활용)
        // 2. 플러시가 아니면 바로 false를 반환하세요
        // 3. 플러시라면 10, J, Q, K, A가 모두 있는지 확인하세요
        // 4. Set<Rank>를 만들어서 필요한 랭크들이 있는지 확인하면 편리합니다
        //
        // 테스트 실패 시 확인사항:
        // - "로열 플러시를 인식하지 못했습니다" 에러: 10, J, Q, K, A 확인 로직이 잘못되었습니다
        // - "일반 플러시를 로열 플러시로 잘못 인식했습니다" 에러: 특정 랭크 확인을 하지 않았습니다

        if(!isFlush()){
            return false;
        }
        Set<Rank> royalRanks = EnumSet.of(Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING, Rank.ACE);
        Set<Rank> handRanks = EnumSet.noneOf(Rank.class);
        for (Card card : cards){
            handRanks.add(card.getRank());
        }
        return handRanks.equals(royalRanks);
    }
    
    /**
     * 스트레이트 플러시인지 확인
     * @return 스트레이트 플러시이면 true
     */
    private boolean isStraightFlush() {
        // TODO: 구현하세요
        //
        // 구현 힌트:
        // 1. 이미 구현된 두 메서드를 활용하세요
        // 2. isFlush() 메서드: 모든 카드가 같은 무늬인지 확인
        // 3. isStraight() 메서드: 카드가 연속된 숫자인지 확인
        // 4. 두 조건을 모두 만족하면 스트레이트 플러시입니다
        //
        // 테스트 실패 시 확인사항:
        // - "스트레이트 플러시를 인식하지 못했습니다" 에러: && 연산자로 두 조건을 확인하지 않았습니다
        // - "일반 스트레이트를 스트레이트 플러시로 잘못 인식했습니다" 에러: 플러시 체크를 하지 않았습니다

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
        Map<Rank, Integer> counts = getRankCounts();
        Collection<Integer> values = counts.values();
        return values.contains(3) && values.contains(2) && values.size() == 2;
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
        Set<Integer> valueSet = new HashSet<>();
        List<Integer> values = new ArrayList<>();
        for (Card card : cards){
            int v = card.getRank().getValue();
            valueSet.add(v);
            values.add(v);
        }
        // 중복이 있으면 스트레이트가 아님
        if (valueSet.size() != 5) return false;
        Collections.sort(values);
        boolean normalStraight = true;
        for (int i = 1; i < values.size(); i++){
            if(values.get(i) - values.get(i-1) != 1){
                normalStraight = false;
                break;
            }
        }
        if(normalStraight) return true;

        // 백스트레이트(A-2-3-4-5) 체크: A=14, 2,3,4,5
        if (values.contains(14) && values.get(0) ==2 && values.get(2) == 4 && values.get(3) ==5){
            return true;
        }
        return false;
    }
    
    /**
     * 쓰리카드인지 확인
     * @return 쓰리카드이면 true
     */
    private boolean isThreeOfAKind() {
        Map<Rank, Integer> counts = getRankCounts();
        Collection<Integer> values = counts.values();
        return values.contains(3) && values.size() == 3;
        
    }
    
    /**
     * 투페어인지 확인
     * @return 투페어이면 true
     */
    private boolean isTwoPair() {
        Map<Rank, Integer> counts = getRankCounts();
        int pairCount = 0;
        for (int count : counts.values()){
            if(count == 2){
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