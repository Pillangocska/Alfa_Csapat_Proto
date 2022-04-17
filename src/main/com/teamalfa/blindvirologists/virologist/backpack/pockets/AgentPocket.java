package main.com.teamalfa.blindvirologists.virologist.backpack.pockets;

import main.com.teamalfa.blindvirologists.AController;
import main.com.teamalfa.blindvirologists.agents.Agent;
import main.com.teamalfa.blindvirologists.agents.virus.Virus;
import main.com.teamalfa.blindvirologists.virologist.backpack.Backpack;

import java.util.ArrayList;

public class AgentPocket extends Pocket{

    private Backpack backpack;
    private ArrayList<Agent> agentHolder = new ArrayList<Agent>();

    public AgentPocket(Backpack b) {
        backpack = b;
        maxSize = 7;
    }

    public void registerObjects() {
        AController.registerObject(this, agentHolder, "agHolder");
    }

    public void addAgent(Agent agent) {
        // Print method call
        AController.printCall(this, "addAgent", new Object[]{agent});

        if(AController.askYesOrNo("Is there enough room in the backpack?")) {
            agentHolder.add(agent);
        }

        // Void method
        AController.printReturn(null);
    }

    public void removeAgent(Agent a) {
        agentHolder.remove(a);
    }

    public int getCurrentSize() {
        return  agentHolder.size();
    }

    public ArrayList<Object> getAgentHolder() {
        ArrayList<Object> agents = new ArrayList<>();
        for(Agent agent : agentHolder)
            agents.add(agent);

        AController.registerObject(null, agents, "agentPocket");
        return agents;
    }
}
