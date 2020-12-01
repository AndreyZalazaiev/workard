package andrew.projects.workard.Service;

import andrew.projects.workard.Domain.Recommendation;
import andrew.projects.workard.Domain.Room;
import andrew.projects.workard.Domain.Visit;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class RecommendationService {

    public static final String OVERFILLED_ROOM_TEXT = " %s has been overcrowded for the last 3 days.";
    public static final String EMPTY_ROOM_TEXT = " %s was empty for 3 days.";

    public HashMap<Integer, Integer> findDensity(ArrayList<Visit> visits) {
        HashMap<Integer, Integer> densityMap = new HashMap<>();//шото треснуло в методе

        for (Visit r : visits
        ) {
            if (densityMap.containsKey(r.getIdRoom())) {
                densityMap.put(r.getIdRoom(), densityMap.get(r.getIdRoom()) + 1);
            } else {
                densityMap.put(r.getIdRoom(), 1);
            }
        }
        return densityMap;
    }

    public ArrayList<Recommendation> generateRecommendations(ArrayList<Visit> visits, ArrayList<Room> rooms) {
        val densityMap = findDensity(visits);
        ArrayList<Room> overfilledRooms = new ArrayList<>();
        ArrayList<Room> emptyRooms = new ArrayList<>();

        densityMap.forEach(
                (k, v) -> {
                    for (Room room : rooms) {
                        if (room.getId() == k && room.getRecommendedValue() < v) {
                            overfilledRooms.add(room);
                        } else if (room.getId() == k && v == 0) {
                            emptyRooms.add(room);
                        }

                    }
                });

        ArrayList<Recommendation> recommendations = formListOfRecommendations(overfilledRooms, emptyRooms);

        return recommendations;
    }

    private ArrayList<Recommendation> formListOfRecommendations(ArrayList<Room> overfilledRooms, ArrayList<Room> emptyRooms) {
        ArrayList<Recommendation> recommendations = new ArrayList<>();
        for (Room room : overfilledRooms
        ) {
            recommendations.add(formRecommendation(OVERFILLED_ROOM_TEXT, room));
        }
        for (Room room : emptyRooms
        ) {
            recommendations.add(formRecommendation(EMPTY_ROOM_TEXT, room));
        }
        return recommendations;
    }

    public Recommendation formRecommendation(String recom, Room room) {
        Recommendation recommendation = new Recommendation();
        recommendation.setIdCompany(room.getIdCompany());
        recommendation.setDate(LocalDateTime.now());
        recommendation.setText(String.format(recom, room.getName()));
        return recommendation;
    }
}
