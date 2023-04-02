import java.util.ArrayList;
import java.util.List;

public class Generation {
    private int id; //Generation ID
    private final int individualSize; //size of the individuals on the generation
    private List<Individual> individualList; //list of individuals
    private final int size; //size of the generation

    public Generation(int individualSize, int size, int id) {
        this.individualSize = individualSize;
        this.id = id;
        this.size = size;
        individualList = new ArrayList<>(); //initializes variable
        //DO Later//
        for (int i = 0; i < size; i++) { //Still not removing duplicates
            Individual ind = new Individual(individualSize); //temporary individual, will later be inserted
            if (!individualList.contains(ind)) {
                //if generated individual is not on the list
                individualList.add(ind);
            } else {
                i--; //tries again in case the generated individual alredy existed
            }
        }
        this.sortIndividuals();
    }

    public List<Individual> getIndividualList() {
        return individualList;
    }

    public int getIndividualSize() {
        return individualSize;
    }

    public int getSize() {
        return size;
    }

    public int getId() {
        return id;
    }

    //Generates mutations on a generation given a list of fathers, it will try to create sets of
    //children of the same size each from their parent
    public void generateMutations(float chanceOfMutation, float percentage) {
        this.id += 1;
        List<Individual> fatherList = this.getFathers(percentage);
        int splitter = (Math.round((float) this.getSize() / fatherList.size())) - 1;
        int fatherIndex = 0; int splitterIndex = 0; boolean duplicates;

        this.getIndividualList().removeAll(this.getIndividualList()); //eliminates all list
        this.getIndividualList().addAll(fatherList);
        //System.out.println("Fathers: " + fatherList);
        int tooAdd = this.getSize() - this.getIndividualList().size();
        for (int i = (this.getSize() - tooAdd); i < this.getSize(); i++){

            if (splitterIndex == splitter){
                splitterIndex = 0;
                fatherIndex++;
                if (fatherIndex == fatherList.size()){
                    fatherIndex--;
                }
            }
            this.getIndividualList().add(new Individual(fatherList.get(fatherIndex), chanceOfMutation));
            duplicates = this.numberOfDuplicates(this.getIndividualList().get(i)) > 1;
            while (duplicates){
                this.getIndividualList().set(i,new Individual(fatherList.get(fatherIndex), chanceOfMutation));
                duplicates = this.numberOfDuplicates(this.getIndividualList().get(i)) > 1;
            }
            //System.out.println("Current: " + this.getIndividualList());
            splitterIndex++;
        }
        this.sortIndividuals();
    }

    public int numberOfDuplicates(Individual individual) {
        int output = 0;
        for (Individual ind : this.getIndividualList()) {
            if (ind.equals(individual)) {
                output++;
            }
        }
        return output;
    }

    public void sortIndividuals() {
        this.getIndividualList().sort((o1, o2) -> o2.evaluateIndividual() - o1.evaluateIndividual());
    }

    public List<Individual> getFathers(float percentage) {
        List<Individual> fatherList = new ArrayList<>();
        this.sortIndividuals();
        List<Individual> genIndividuals = this.getIndividualList();
        int numberOfFathers = (int) (this.getSize() * percentage);
        for (int i = 0; i < numberOfFathers; i++) {
            fatherList.add(genIndividuals.get(i));
        }
        return fatherList;
    }

    @Override
    public String toString() {
        String output = "Generation " + id + "\n";
        for (int i = 0; i < this.size; i++) {
            output += "Individual " + i + " |" + individualList.get(i) + "| " + individualList.get(i).evaluateIndividual() + "\n";
        }
        return output;
    }

}
