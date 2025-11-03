package lotto;
//로또 한장이 가지는 번호, 검증, 출력 기능을 여기서 책임

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lotto {
    private final List<Integer> numbers;

    // 로또 번호 저장
    public Lotto(List<Integer> numbers) {
        validate(numbers); //유효성 검사
        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        Collections.sort(sortedNumbers); //오름차순으로 정리
        this.numbers = Collections.unmodifiableList(sortedNumbers);
    }

    // 로또 번호 검증 (번호 6개 확인, 번호 중복 불가, 번호는 1부터 45 사이의 숫자)
    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }

        long checkDuplicate = numbers.stream().distinct().count();
        if (checkDuplicate != numbers.size()) {
            throw new IllegalArgumentException("[ERROR] 로또 번호가 중복됐습니다.");
        }

        boolean checkRange = numbers.stream().anyMatch(number -> number <1 || number > 45);
        if (checkRange) {
            throw new IllegalArgumentException("[ERROR] 로또 번호가 1부터 45 사이의 숫자가 아닙니다.");
        }
        //오름차순 정리인지 검증을 하지 않는 이유는 오름차순 정렬을 생성자에서 직접 수행하기때문이다. 실패할일이 없음
    }

    // 안전하게 번호만 읽을수 있도록
    public List<Integer> getNumbers() {
        return numbers;
    }

    //출력 형식을 일관적으로
    @Override
    public String toString() {
        return numbers.toString();
    }
}
