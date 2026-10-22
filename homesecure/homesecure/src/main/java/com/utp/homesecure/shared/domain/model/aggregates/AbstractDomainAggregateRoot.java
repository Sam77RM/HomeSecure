package com.utp.homesecure.shared.domain.model.aggregates;

import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.Collection;

public abstract class AbstractDomainAggregateRoot
        <T extends AbstractDomainAggregateRoot<T>> extends
        AbstractAggregateRoot<T> {

    protected void registerDomainEvent(Object event) {
        super.registerEvent(event);
    }

    @Override
    protected Collection<Object> domainEvents() {
        return super.domainEvents();
    }

    @Override
    protected void clearDomainEvents() {
        super.clearDomainEvents();
    }
}
