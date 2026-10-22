package com.utp.platform.learning.infrastructure.events;

import com.utp.platform.shared.domain.events.DomainEventDispatcher;
import com.utp.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Objects;

/** Dispatches events only after a successful database transaction commit. */
@Component
public class SpringDomainEventDispatcher implements DomainEventDispatcher {
    private final ApplicationEventPublisher publisher;

    public SpringDomainEventDispatcher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void dispatch(AbstractDomainAggregateRoot<?> aggregate) {
        Objects.requireNonNull(aggregate, "aggregate");
        if (!TransactionSynchronizationManager.isActualTransactionActive()
                || !TransactionSynchronizationManager.isSynchronizationActive()) {
            throw new IllegalStateException("Domain event dispatch requires an active Spring transaction");
        }
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                aggregate.pullDomainEvents().forEach(publisher::publishEvent);
            }
        });
    }
}
