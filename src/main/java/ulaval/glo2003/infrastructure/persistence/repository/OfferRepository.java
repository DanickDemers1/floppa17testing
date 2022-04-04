package ulaval.glo2003.infrastructure.persistence.repository;

import dev.morphia.query.experimental.filters.Filters;
import ulaval.glo2003.application.exceptions.ItemNotFoundException;
import ulaval.glo2003.entities.offer.Offer;
import ulaval.glo2003.infrastructure.persistence.DbConnection;
import ulaval.glo2003.infrastructure.persistence.assemblers.OfferDbAssembler;
import ulaval.glo2003.infrastructure.persistence.models.BuyerModel;
import ulaval.glo2003.infrastructure.persistence.models.OfferModel;
import ulaval.glo2003.infrastructure.persistence.models.ProductModel;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OfferRepository implements Repository<Offer> {
    private final DbConnection dbConnection;

    public OfferRepository(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Offer get(UUID id) {
        var model = dbConnection.getDataStore()
                .find(OfferModel.class)
                .filter(Filters.eq("_id", id))
                .first();

        if (model == null)
            throw new ItemNotFoundException();

        return OfferDbAssembler.toEntity(model);
    }

    @Override
    public Boolean exists(UUID id) {
        var model = dbConnection.getDataStore()
                .find(OfferModel.class)
                .filter(Filters.eq("_id", id))
                .first();

        return model != null;
    }

    @Override
    public void save(Offer offer) {
        var productModel = dbConnection.getDataStore()
                .find(ProductModel.class)
                .filter(Filters.eq("_id", offer.getProductId()))
                .first();

        var buyerModel = dbConnection.getDataStore()
                .find(BuyerModel.class)
                .filter(Filters.eq("_id", offer.getBuyerId()))
                .first();

        dbConnection.getDataStore().save(OfferDbAssembler.toModel(offer, productModel, buyerModel));
    }

    @Override
    public void remove(UUID entityId) {
    }

    @Override
    public List<Offer> getAll(){
        return dbConnection.getDataStore()
                .find(OfferModel.class).iterator().toList()
                .stream().map(OfferDbAssembler::toEntity)
                .collect(Collectors.toList());
    }

    public List<Offer> getOffersForProduct(UUID productId) {
        var productModel = dbConnection.getDataStore()
                .find(ProductModel.class)
                .filter(Filters.eq("_id", productId))
                .first();

        return dbConnection.getDataStore()
                .find(OfferModel.class).filter(Filters.eq("productModel", productModel))
                .iterator().toList()
                .stream().map(OfferDbAssembler::toEntity)
                .collect(Collectors.toList());
    }
}
