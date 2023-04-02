import java.util.ArrayList;
import java.util.List;

public class Individual {

    private final int size;
    //private int[] genes;
    private List<Integer> genes; // represent the row position
    //Random Constructor, assigns random values to all genes of an individual
    public Individual(int size) {
        this.size = size;
        //genes = new int[size];
        genes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            genes.add(i,(int) (Math.random() * size));
            //genes[i] = (int) (Math.random() * size);
        }
    }
    //Manual constructor only for testing
    public Individual(int a, int b, int c, int d, int e, int f, int g, int h) {
        this.size = 8;
        genes = new ArrayList<>();
        //genes = new int[size];
        genes.add(a);
        genes.add(b);
        genes.add(c);
        genes.add(d);
        genes.add(e);
        genes.add(f);
        genes.add(g);
        genes.add(h);
    }

    public Individual(Individual individual){
        this.size = individual.getSize();
        genes = new ArrayList<>();
        this.setGenes(individual.getGenes());

    }

    public Individual(Individual individual, float percentage){
        this.size = individual.getSize();
        genes = new ArrayList<>();
        for (int i = 0; i < this.getSize(); i++){
            if (Math.random() < percentage){
                this.getGenes().add((int)(Math.random() * this.getSize()));
            }else{
                this.getGenes().add(individual.getRow(i));
            }
        }

    }

    private static boolean isValidPosition(int x, int y, int size, Individual individual) {
        return x < size && y < size && x >= 0 && y >= 0 && y == individual.getRow(x);
    }

    public List<Integer> getGenes() {
        return genes;
    }

    public int getSize() {
        return size;
    }

    public int getRow(int column) {
        if (column < this.getSize()) {
            return genes.get(column);
        }
        return -1; // if argument is not valid
    }

    public void setGenes(List<Integer> newGenes) {
        if (newGenes.size() != this.getSize()){
            return;
        }
        this.genes = newGenes;
    }

    public int evaluateIndividual() {
        int numberOfFlaws = 0;
        //HorizontalFlaws
        for (int i = 0; i < this.getSize() - 1; i++) {
            for (int j = i + 1; j < this.getSize(); j++) {
                if (this.getRow(i) == this.getRow(j)) {
                    numberOfFlaws++;
                }
            }
        }

        //DiagonalUp and DiagonalDown Flaws
        for (int i = 0; i < this.getSize() - 1; i++){
            //System.out.println("Piece: (" + i + "," + this.getRow(i) + "):");
            int diagonalDownIndex = 1;
            int diagonalUpIndex = 1;
            for ( int j = i; j < this.getSize(); j++){
                //System.out.println("Compare (" + i + "," + this.getRow(i) + ") with (" + (j+1) + "," + this.getRow(j+1) + ")");

                //Tests if the DiagonalUp position exists in the grid (size*size)
                if (!isValidPosition(j + 1,this.getRow(j + 1), this.getSize(), this)) break;
                if (this.getRow(i) == this.getRow(j + 1) - diagonalDownIndex) {
                    numberOfFlaws++;
                    //System.out.println("DiagonalUp (" + i + "," + this.getRow(i) + ") can eat (" + (j+1) + "," + this.getRow(j+1) + ")");
                }
                diagonalDownIndex++;
                //Tests if the DiagonalDown position exists in the grid (size*size)
                //System.out.println("Compare (" + i + "," + this.getRow(i) + ") with (" + (j+1) + "," + this.getRow(j+1) + ")");
                if (!isValidPosition(j + 1,this.getRow(j + 1), this.getSize(), this)) break;
                if (this.getRow(i) == this.getRow(j + 1) + diagonalUpIndex) {
                    numberOfFlaws++;
                    //System.out.println("DiagonalDown (" + i + "," + this.getRow(i) + ") can eat (" + (j+1) + "," + this.getRow(j+1) + ")");
                }
                diagonalUpIndex++;
            }
        }

        return ((this.getSize() * (this.getSize() - 1)) / 2) - numberOfFlaws;
    }

    @Override
    public String toString() {
        String output = "Individual: | ";
        for (int i = 0; i < this.getSize(); i++) {
            output += this.getRow(i) + " ";
        }
        output+= "| " + this.evaluateIndividual();
        return output;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Individual){
            boolean output = true;
            for (int i = 0; i < this.getSize(); i++) {
                if (this.getRow(i) != ((Individual) obj).getRow(i)) {
                    output = false;
                }
            }
            return output;
        }
            return false;
    }
}
