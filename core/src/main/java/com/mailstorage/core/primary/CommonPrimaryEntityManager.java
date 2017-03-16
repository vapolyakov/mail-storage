package com.mailstorage.core.primary;

import java.util.List;

/**
 * @author metal
 */
public class CommonPrimaryEntityManager<I, M extends PrimaryEntityManager> {
    private List<M> managers;

    public CommonPrimaryEntityManager(List<M> managers) {
        this.managers = managers;
    }

    public PrimaryEntitiesRegistry calculateEntities(I input) {
        final PrimaryEntitiesRegistry registry = new PrimaryEntitiesRegistry();
        managers.stream().forEach(manager -> registry.registerPrimaryEntity(manager.extractAndSave(input)));
        return registry;
    }
}
