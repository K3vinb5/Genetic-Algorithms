public class Main {

    public static void main(String[] args) {
        //Variables
        int individualSize = 15;
        int generationSize = 100;
        int maxIterations = 10000;
        boolean solutionFound = false;

        long startTime = System.currentTimeMillis();

        Generation genA = new Generation(individualSize,generationSize,0);
        //print(genA);
        for (int i = 0; i < maxIterations; i++){
            genA.generateMutations(0.3f,0.2f);
            //print(genA);
            if (genA.getIndividualList().get(0).evaluateIndividual() == ((genA.getIndividualSize()*(genA.getIndividualSize() - 1))/2)){
                print("Generation " + genA.getId() + " | Board Size: " + genA.getIndividualSize() + "\n\n" +  genA.getIndividualList().get(0));
                solutionFound = true;
                break;
            }
        }
        if (!solutionFound){
            System.out.println("Solution not found");
        }
        long endTime = System.currentTimeMillis();

        System.out.println("\nExecution Time: " + ((endTime - startTime) / 1000.0) + " seconds");
    }

    private static void print(Object obj){
        System.out.println(obj);
    }

}