package com.mailstorage.core.primary;

import java.util.List;

/**
 * @author metal
 *
 * Allows to extract and save all entities for specific input data and managers.
 * @param <I> input data type
 * @param <M> primary entity manager class that controls entities extraction and saving
 */
public class CommonPrimaryEntityManager<I, M extends PrimaryEntityManager> {
    private List<M> managers;

    public CommonPrimaryEntityManager(List<M> managers) {
        this.managers = managers;
    }

    /**
     * Extract and save all entities for specific input data and managers.
     * @param input input data
     * @return registry that contains all calculated entities
     */
    public PrimaryEntitiesRegistry calculateEntities(I input) {
        final PrimaryEntitiesRegistry registry = new PrimaryEntitiesRegistry();
        managers.stream().forEach(manager -> registry.registerPrimaryEntity(manager.extractAndSave(input)));
        return registry;
    }
}
