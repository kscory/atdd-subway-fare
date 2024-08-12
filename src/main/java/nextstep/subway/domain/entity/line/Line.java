package nextstep.subway.domain.entity.line;

import lombok.Builder;
import lombok.Getter;
import nextstep.subway.domain.command.LineCommand;

import javax.persistence.*;
import java.util.Optional;

@Getter
@Entity(name = "lines")
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String color;

    @Embedded
    private LineSections sections;

    protected Line() {
    }

    @Builder
    public Line(String name, String color, LineSections sections) {
        this.name = name;
        this.color = color;
        this.sections = sections;
    }

    public static Line init(LineCommand.CreateLine command) {
        Line line = new Line(command.getName(), command.getColor(), new LineSections());
        line.addSection(command.getUpStationId(), command.getDownStationId(), command.getDistance(), command.getDuration());
        return line;
    }

    public void update(LineCommand.UpdateLine command) {
        this.name = command.getName();
        this.color = command.getColor();
    }

    public void addSection(Long upStationId, Long downStationId, Long distance, Long duration) {
        sections.addSection(new LineSection(this, upStationId, downStationId, distance, duration));
    }

    public void deleteSection(Long stationId) {
        sections.deleteSection(stationId);
    }

    public Optional<LineSection> findSectionByUpDownStationId(Long upStationId, Long downStationId) {
        return sections.findSectionByUpDownStationID(upStationId, downStationId);
    }
}
