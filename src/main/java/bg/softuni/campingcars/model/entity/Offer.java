package bg.softuni.campingcars.model.entity;

import bg.softuni.campingcars.model.enums.EngineEnum;
import bg.softuni.campingcars.model.enums.TransmissionEnum;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;

import java.math.BigDecimal;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "offers")
@Getter
public class Offer extends BaseEntity {

    @JdbcTypeCode(Types.VARCHAR)
    private UUID uuid;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private long mileage;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EngineEnum engine;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransmissionEnum transmission;

    @Column(nullable = false)
    private int horsePower;

    @Column(nullable = false)
    private int beds;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne
    private User seller;

    @ManyToOne
    private Category category;

    public Offer setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public Offer setDescription(String description) {
        this.description = description;
        return this;
    }

    public Offer setMileage(long mileage) {
        this.mileage = mileage;
        return this;
    }

    public Offer setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Offer setEngine(EngineEnum engine) {
        this.engine = engine;
        return this;
    }

    public Offer setTransmission(TransmissionEnum transmission) {
        this.transmission = transmission;
        return this;
    }

    public Offer setHorsePower(int horsePower) {
        this.horsePower = horsePower;
        return this;
    }

    public Offer setBeds(int beds) {
        this.beds = beds;
        return this;
    }

    public Offer setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public Offer setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Offer setSeller(User seller) {
        this.seller = seller;
        return this;
    }

    public Offer setCategory(Category category) {
        this.category = category;
        return this;
    }
}
