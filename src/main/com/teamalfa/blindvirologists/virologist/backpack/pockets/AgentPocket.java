package main.com.teamalfa.blindvirologists.virologist.backpack.pockets;

import main.com.teamalfa.blindvirologists.agents.Agent;
import main.com.teamalfa.blindvirologists.virologist.backpack.Backpack;

import java.util.ArrayList;

public class AgentPocket extends Pocket{

    private Backpack backpack;
    private ArrayList<Agent> agentHolder = new ArrayList<Agent>();

    public AgentPocket(Backpack b) {
        backpack = b;
        maxSize = 7;
    }

    public boolean addAgent(Agent agent) {
        if(agentHolder.size() < maxSize) {
            agentHolder.add(agent);
            return true;
        }
        return false;
    }

    public void removeAgent(Agent a) {
        agentHolder.remove(a);
    }

    public int getCurrentSize() {
        return  agentHolder.size();
    }

    public ArrayList<Agent> getAgentHolder() {
        return agentHolder;
    }
}
