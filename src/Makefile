JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class: 
	$(JC) $(JFLAGS) $*.java

# This uses the line continuation character (\) for readability
# You can list these all on a single line, separated by a space instead.
# If your version of make can't handle the leading tabs on each
# line, just remove them (these are also just added for readability).
CLASSES = \
	main/com/teamalfa/blindvirologists/agents/genetic_code/AmnesiaCode.java \
	main/com/teamalfa/blindvirologists/agents/genetic_code/DanceCode.java \
	main/com/teamalfa/blindvirologists/agents/genetic_code/GeneticCode.java \
	main/com/teamalfa/blindvirologists/agents/genetic_code/ParalyzeCode.java \
	main/com/teamalfa/blindvirologists/agents/virus/AmnesiaVirus.java \
	main/com/teamalfa/blindvirologists/agents/virus/DanceVirus.java \
	main/com/teamalfa/blindvirologists/agents/virus/ParalyzeVirus.java \
	main/com/teamalfa/blindvirologists/agents/virus/Virus.java \
	main/com/teamalfa/blindvirologists/agents/virus/VirusComparator.java \
	main/com/teamalfa/blindvirologists/agents/Agent.java \
	main/com/teamalfa/blindvirologists/agents/GeneticCodeBank.java \
	main/com/teamalfa/blindvirologists/agents/Vaccine.java \
	main/com/teamalfa/blindvirologists/city/fields/Field.java \
	main/com/teamalfa/blindvirologists/city/fields/Laboratory.java \
	main/com/teamalfa/blindvirologists/city/fields/SafeHouse.java \
	main/com/teamalfa/blindvirologists/city/fields/StoreHouse.java \
	main/com/teamalfa/blindvirologists/city/City.java \
	main/com/teamalfa/blindvirologists/equipments/active_equipments/ActiveEquipment.java \
	main/com/teamalfa/blindvirologists/equipments/active_equipments/Gloves.java \
	main/com/teamalfa/blindvirologists/equipments/Bag.java \
	main/com/teamalfa/blindvirologists/equipments/Cloak.java \
	main/com/teamalfa/blindvirologists/turn_handler/Steppable.java \
	main/com/teamalfa/blindvirologists/turn_handler/TurnHandler.java \
	main/com/teamalfa/blindvirologists/virologist/backpack/pockets/AgentPocket.java \
	main/com/teamalfa/blindvirologists/virologist/backpack/pockets/EquipmentPocket.java \
	main/com/teamalfa/blindvirologists/virologist/backpack/pockets/GeneticCodePocket.java \
	main/com/teamalfa/blindvirologists/virologist/backpack/pockets/Pocket.java \
	main/com/teamalfa/blindvirologists/virologist/backpack/Backpack.java \
	main/com/teamalfa/blindvirologists/virologist/backpack/ElementBank.java \
	main/com/teamalfa/blindvirologists/virologist/Virologist.java \
	main/com/teamalfa/blindvirologists/AController.java \
	main/com/teamalfa/blindvirologists/Main.java 

default: classes

classes: $(CLASSES:.java=.class)

clean: 
	$(RM) *.class
