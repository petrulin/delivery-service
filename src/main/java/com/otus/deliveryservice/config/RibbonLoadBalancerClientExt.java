package com.otus.deliveryservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.stereotype.Component;

@Component
public class RibbonLoadBalancerClientExt extends RibbonLoadBalancerClient {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Override
    public <T> ServiceInstance choose(String serviceId, Request<T> request) {
        return this.loadBalancerClient.choose(serviceId);
    }

    public RibbonLoadBalancerClientExt(SpringClientFactory clientFactory) {
        super(clientFactory);
    }

}
