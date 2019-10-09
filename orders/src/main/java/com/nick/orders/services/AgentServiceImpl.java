package com.nick.orders.services;

import com.nick.orders.models.Agent;
import com.nick.orders.models.Customer;
import com.nick.orders.repos.AgentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Service(value = "agentservice")
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentRepo agentRepos;

    @Override
    public void delete(long agentcode) {
        Agent currentAgent = agentRepos.findById(agentcode)
                .orElseThrow(() -> new EntityNotFoundException(Long.toString(agentcode)));
        if(currentAgent.getCustomers().size() == 0) {
            agentRepos.deleteById(agentcode);
        } else {
            throw new EntityExistsException("The current agent still has existing customers");
        }
    }
}
