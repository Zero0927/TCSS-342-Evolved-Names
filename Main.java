//Siyuan Zhou,Feng Yang
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class Main {

	public static void main(String[] args) throws IOException {
		 File trace = new File("trace.txt");
		 trace.createNewFile();
		 PrintStream ps = new PrintStream(trace);
		 String target = "CHRISTOPHER PAUL MARRIOTT";
		 Population pp = new Population(100, 0.05);
		 pp.setTargetName(target);
		 int i=0;
		 long timeStart = System.currentTimeMillis();
		 do{
		 pp.day();
		 ps.write(((pp.getMostFit()).toString()+"\n").getBytes());
		 i++;
		 }while(pp.getMostFit().getFitness()!=0);
		 ps.write(("Generations: "+i+"\n").getBytes());
		 ps.write(("Running Time: "+(System.currentTimeMillis()-timeStart)+" milliseconds").getBytes());
		 ps.close();
	}

	public void testGenome() {
		Genome genome = new Genome(0.05);
		genome.setTargetName("CHRISTOPHER PAUL MARRIOTT");
		Genome genomeClone = new Genome(genome);
		System.out.println(genome);
		genome.mutate();
		System.out.println(genome);
		genome.mutate();
		System.out.println(genome);
		genome.crossover(genomeClone);
		System.out.println(genome);
		System.out.println(genome.fitness());
		System.out.println(genome.fitness2());

	}

	public void testPopulation() {
		Population pop = new Population(30, 0.5);
		pop.setTargetName("CHRISTOPHER PAUL MARRIOTT");
		System.out.println(pop.getMostFit());
		pop.day();
		System.out.println(pop.getMostFit());
	}
}
