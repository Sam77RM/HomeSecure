package com.utp.platform.shared.domain.events;

import com.utp.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

/** Port for dispatching events recorded by a domain aggregate. */
public interface DomainEventDispatcher {
    void dispatch(AbstractDomainAggregateRoot<?> aggregate);
}
