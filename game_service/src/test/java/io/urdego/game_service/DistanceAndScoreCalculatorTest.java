package io.urdego.game_service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DistanceAndScoreCalculatorTest {

    private final double tolerance = 0.5;       // 거리 계산 오차 허용 범위(100m)
    private final double maxDistance = 300.0;   // 점수가 0이 되는 기준 거리
    private final int maxScore = 1000;          // 최대 점수

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;     // 지구 반지름 (km)
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;   // 결과 거리 (km 단위)
    }

    private int calculateScore(double distance) {
        if (distance > maxDistance) {
            return 0;   // 최대 거리를 초과하면 점수 0점
        }
        return (int) Math.max(0, maxScore - (distance / maxDistance) * maxScore);
    }

    @Test
    public void testCalculateDistance() {
        // 서울(37.5665, 126.978) -> 부산(35.1796, 129.0756)
        double distance = calculateDistance(37.5665, 126.978, 35.1796, 129.0756);
        System.out.println("Distance: " + distance + " km");
        double expectedDistance = 325.0; // 예상 거리
        assertTrue(Math.abs(distance - expectedDistance) <= tolerance,
                "Expected distance around " + expectedDistance + " km, but got " + distance);
    }

    @Test
    public void testCalculateScore() {
        // 점수 테스트
        assertEquals(1000, calculateScore(0));      // 같은 위치
        assertEquals(666, calculateScore(100));     // 100km
        assertEquals(333, calculateScore(200));     // 200km
        assertEquals(0, calculateScore(300));       // 300km
        assertEquals(0, calculateScore(350));       // 초과 거리
    }

    @Test
    public void testIntegration() {
        // 서울 -> 부산 (325km)
        double distance = calculateDistance(37.493973, 126.924372, 37.479916, 127.013539);
        int score = calculateScore(distance);
        System.out.println("Score: " + score);
        assertEquals(973, score);
    }
}
