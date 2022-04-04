package ulaval.glo2003.infrastructure.persistence.repository;

import dev.morphia.query.experimental.filters.Filters;
import ulaval.glo2003.application.exceptions.ItemNotFoundException;
import ulaval.glo2003.entities.seller.Seller;
import ulaval.glo2003.infrastructure.persistence.DbConnection;
import ulaval.glo2003.infrastructure.persistence.assemblers.SellerDbAssembler;
import ulaval.glo2003.infrastructure.persistence.models.SellerModel;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SellerRepository implements Repository<Seller> {
    private final DbConnection dbConnection;

    public SellerRepository(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Seller get(UUID id) {
        var sellerModel = dbConnection.getDataStore().find(SellerModel.class).
                filter(Filters.eq("_id", id)).first();

        if (sellerModel == null)
            throw new ItemNotFoundException();

        return SellerDbAssembler.toEntity(sellerModel);
    }

    @Override
    public Boolean exists(UUID id) {
        var model = dbConnection.getDataStore().find(SellerModel.class).filter(Filters.eq("_id", id)).first();
        return model != null;
    }

    @Override
    public void save(Seller entity) {
        dbConnection.getDataStore().save(SellerDbAssembler.toModel(entity));
    }

    @Override
    public void remove(UUID entityId) {
    }

    @Override
    public List<Seller> getAll(){
        return dbConnection.getDataStore()
                .find(SellerModel.class).iterator().toList()
                .stream().map(SellerDbAssembler::toEntity)
                .collect(Collectors.toList());
    }

}
