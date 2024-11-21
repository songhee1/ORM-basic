package jpabook.jpashop.test;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
@Embeddable // 값 타입 정의
public class Period {
    // Period
    LocalDateTime startDate;
    LocalDateTime endDate;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Period() {
    }
}
