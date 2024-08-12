package nextstep.subway.domain.testing;

import nextstep.subway.domain.entity.line.Line;
import nextstep.subway.domain.entity.line.LineSection;
import nextstep.subway.domain.entity.station.Station;
import nextstep.subway.domain.repository.LineRepository;
import nextstep.subway.fixtures.LineFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
public class LineDbUtil {
    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Line insertLine(Long upStationId, Long downStationId) {
        Line line = LineFixture.prepareRandom(upStationId, downStationId);
        return lineRepository.save(line);
    }

    @Transactional
    public Line insertConnectedLine(Station... stations) {
        Line line = LineFixture.prepareRandom(stations[0].getId(), stations[1].getId());
        lineRepository.save(line);


        return lineRepository.save(line);
    }

    @Transactional
    public LineSection insertSection(Line line, Long upStationId, Long downStationId, Long distance, Long duration) {
        LineSection section = new LineSection(line, upStationId, downStationId, distance, duration);
        entityManager.persist(section);
        return section;
    }
}
