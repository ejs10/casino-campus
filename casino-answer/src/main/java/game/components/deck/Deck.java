package game.components.deck;

import game.components.card.Card;
import game.components.card.Rank;
import game.components.card.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 카드 덱을 나타내는 클래스
 * 
 * 이 클래스는 카드 게임에서 사용되는 카드 덱의 기본 동작을 정의합니다.
 * 표준 덱은 52장의 카드(4개 무늬 × 13개 랭크)로 구성됩니다.
 * 
 * <p>구현 요구사항:</p>
 * <ul>
 *   <li>새 덱은 52장의 카드를 모두 포함해야 합니다</li>
 *   <li>카드를 뽑으면 덱에서 제거되어야 합니다</li>
 *   <li>셔플은 카드의 순서를 무작위로 섞어야 합니다</li>
 *   <li>한 번 사용한 덱은 폐기하고 새 덱을 생성해야 합니다</li>
 *   <li>적절한 예외 처리를 해야 합니다</li>
 * </ul>
 * 
 * <p>카지노 실무 규칙:</p>
 * <ul>
 *   <li>매 게임마다 새로운 덱 사용</li>
 *   <li>사용한 카드는 재사용하지 않음 (보안 및 공정성)</li>
 *   <li>카드 카운팅 방지를 위해 여러 덱을 함께 사용</li>
 *   <li>플라스틱 및 봉인된 새 덱 사용</li>
 * </ul>
 * 
 * <p>사용 예시:</p>
 * <pre>
 * // 매 게임마다 새 덱 생성
 * Deck deck = new Deck();
 * deck.shuffle();
 * 
 * // 게임 진행
 * Card card = deck.drawCard();
 * 
 * // 게임 종료 후 덱은 폐기
 * // 다음 게임을 위해 새 덱 생성 필요
 * deck = new Deck();
 * deck.shuffle();
 * </pre>
 * 
 * 구현이 필요한 부분:
 * - cards 필드 초기화: 52장의 카드 생성
 * - shuffle() 메서드: 카드 섞기
 * - drawCard() 메서드: 카드 한 장 뽑기
 * - isEmpty() 메서드: 덱이 비어있는지 확인
 * 
 * @author XIYO
 * @version 1.0
 * @since 2024-01-01
 */
public class Deck {
    // TODO: 구현하세요 - 인스턴스 초기화 블록 추가
    // 
    // 🎯 구현 순서:
    // 1. 인스턴스 초기화 블록을 만들어야 합니다
    //    - 초기화 블록은 { }로 감싸진 코드 영역입니다
    //    - 클래스 내부에 필드 선언 아래에 작성합니다
    //    - 예: { /* 여기에 초기화 코드 작성 */ }
    // 
    // 2. 52장의 카드를 생성하는 로직
    //    - 바깥 반복문: Suit.values()로 모든 무늬 순회 (SPADES, HEARTS, DIAMONDS, CLUBS)
    //    - 안쪽 반복문: Rank.values()로 모든 랭크 순회 (TWO부터 ACE까지)
    //    - 각 조합에 대해 new Card(suit, rank)로 카드 생성
    //    - cards.add()로 리스트에 추가
    // 
    // 3. 세부 구현 힌트
    //    - for-each 문법 사용: for (Suit suit : Suit.values())
    //    - 각 무늬마다 13장의 카드가 생성되어야 합니다
    //    - 총 4 × 13 = 52장이 만들어집니다
    // 
    // 테스트 실패 시 확인사항:
    // - "덱은 52장의 카드를 가져야 합니다" 에러: 반복문이 잘못되었거나 추가를 빼먹었습니다
    // - "빈 컬렉션입니다" 경고: 초기화 블록을 만들지 않았습니다
    // - NullPointerException: Card 생성자에 null을 전달했습니다
    
    private final List<Card> cards = new ArrayList<>();
    
    // 인스턴스 초기화 블록 - 52장의 카드 생성
    {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }
    
    /**
     * 덱을 섞습니다.
     * 
     * 카드의 순서를 무작위로 변경합니다.
     * 셔플 후에도 덱의 카드 수는 변하지 않습니다.
     * 
     * <p>카지노 규칙:</p>
     * 새로운 덱은 사용 전에 반드시 섞어야 합니다.
     */
    public void shuffle() {
        // TODO: 구현하세요
        // 
        // 🎯 구현 순서:
        // 1. 필요한 클래스 import
        //    - java.util.Collections 클래스가 필요합니다
        //    - 이미 import되어 있다면 그대로 사용하면 됩니다
        // 
        // 2. Collections 클래스의 메서드 활용
        //    - Collections 클래스에는 리스트를 섞는 static 메서드가 있습니다
        //    - 메서드 이름은 shuffle입니다
        //    - 호출 형태: Collections.shuffle(리스트)
        // 
        // 3. 인자 전달
        //    - 이 클래스의 cards 필드를 인자로 전달합니다
        //    - shuffle 메서드는 전달받은 리스트를 직접 수정합니다
        // 
        // 테스트 실패 시 확인사항:
        // - "카드 순서가 변경되지 않았습니다" 에러: Collections.shuffle()을 호출하지 않았습니다
        // - "카드 수가 변경되었습니다" 에러: 다른 메서드를 사용했거나 추가 로직을 넣었습니다
        
        Collections.shuffle(cards);
    }
    
    /**
     * 덱에서 카드를 한 장 뽑습니다.
     * 
     * 덱의 맨 위에서 카드를 한 장 뽑아 반환합니다.
     * 뽑은 카드는 덱에서 제거됩니다.
     * 
     * @return 뽑은 카드
     * @throws IllegalStateException 덱이 비어있을 때
     */
    public Card drawCard() {
        // TODO: 구현하세요
        // 
        // 구현 힌트:
        // 1. 먼저 덱이 비어있는지 확인하세요 (isEmpty() 메서드 활용)
        // 2. 비어있다면 IllegalStateException을 던지세요
        // 3. 카드가 있다면 리스트에서 하나를 제거하고 반환하세요
        // 4. 맨 앞(인덱스 0) 또는 맨 뒤에서 제거할 수 있습니다
        // 
        // 테스트 실패 시 확인사항:
        // - "덱이 비어있습니다" 에러: isEmpty() 체크를 하지 않았습니다
        // - "카드가 제거되지 않았습니다" 에러: remove() 메서드를 호출하지 않았습니다
        // - IndexOutOfBoundsException: 빈 리스트에서 카드를 뽑으려고 했습니다
        
        if (isEmpty()) {
            throw new IllegalStateException("덱이 비어있습니다.");
        }
        return cards.remove(0);
    }
    
    /**
     * 덱이 비어있는지 확인합니다.
     * 
     * @return 덱이 비어있으면 true, 카드가 하나라도 있으면 false
     */
    public boolean isEmpty() {
        // TODO: 구현하세요
        // 
        // 구현 힌트:
        // 1. cards 리스트가 비어있는지 확인하면 됩니다
        // 2. List 인터페이스에는 이를 확인하는 메서드가 있습니다
        // 
        // 테스트 실패 시 확인사항:
        // - "덱이 비어있지 않은데 true를 반환했습니다" 에러: 조건을 반대로 구현했습니다
        // - "덱이 비어있는데 false를 반환했습니다" 에러: isEmpty() 로직이 잘못되었습니다
        
        return cards.isEmpty();
    }
}