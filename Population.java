//Siyuan Zhou,Feng Yang
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

public class Population {

	private String targetName = null;
	// private LinkedList<Genome> genomes = null;
	private Genome[] genomes = null;
	private int numGenomes;
	private Genome mostFit = null;

	/**
	 * constructors
	 * 
	 * @param numGenomes
	 * @param mutationRate
	 */
	public Population(Integer numGenomes, Double mutationRate) {
		// genomes = new LinkedList<Genome>();
		genomes = new Genome[numGenomes];
		this.numGenomes = numGenomes;
		for (int i = 0; i < numGenomes; i++) {
			Genome newGenome = new Genome(mutationRate);
			genomes[i] = newGenome;
		}
	}

	public void day() {

		// sort the genomes based on fitness of genome which is in genomes
		mergeSort(genomes, 0, genomes.length - 1, new Genome[genomes.length]);
		// update the mostFit of population
		mostFit = genomes[0];
		// we can ignore the process of deleting, and we can Override the genome
		// in the second behalf
		int i = numGenomes / 2;
		int j = numGenomes / 2;

		// create new genomes from the remaining population
		// until the number of genomes is restored
		while (i++ < numGenomes - 1) {
			Random random = new Random();
			// pick a remaining genome at random
			Genome genomeSelect = genomes[random.nextInt(j)];
			// select a method randomly
			int methodSelected = random.nextInt(2);
			if (methodSelected == 0) {
				// select method one
				Genome genomeAdd = new Genome(genomeSelect);
				genomeAdd.mutate();
				genomes[i] = genomeAdd;
			} else {
				// select method two
				Genome genomeAdd = new Genome(genomeSelect);
				Genome genomeSlected2 = genomes[random.nextInt(j)];
				genomeAdd.crossover(genomeSlected2);
				genomeAdd.mutate();
				genomes[i] = genomeAdd;
			}
		}
	}

	/**
	 * merge two array of genomes to one array in increase order
	 * 
	 * @param genomes
	 * @param first
	 * @param mid
	 * @param last
	 * @param genomeTemp
	 */
	private void mergeArray(Genome[] genomes, int first, int mid, int last,
			Genome[] genomeTemp) {
		int i = first, j = mid + 1, m = mid, n = last, k = 0;
		while (i <= m && j <= n) {
			if (genomes[i].getFitness() <= genomes[j].getFitness())
				genomeTemp[k++] = genomes[i++];
			else
				genomeTemp[k++] = genomes[j++];
		}
		while (i <= m)
			genomeTemp[k++] = genomes[i++];
		while (j <= n)
			genomeTemp[k++] = genomes[j++];
		for (i = 0; i < k; i++)
			genomes[first + i] = genomeTemp[i];
	}

	/**
	 * merge sort the genomes
	 * 
	 * @param genomes
	 *            origin genomes
	 * @param first
	 *            first index
	 * @param last
	 *            last index
	 * @param genomeTemp
	 *            temporary genome
	 */
	private void mergeSort(Genome[] genomes, int first, int last,
			Genome[] genomeTemp) {
		if (first < last) {
			int mid = (first + last) / 2;
			mergeSort(genomes, first, mid, genomeTemp);
			mergeSort(genomes, mid + 1, last, genomeTemp);
			mergeArray(genomes, first, mid, last, genomeTemp);
		}
	}

	/* getters and setters */

	/**
	 * get the mostFit of population
	 * 
	 * @return the mostFit of population
	 */
	public Genome getMostFit() {
		return mostFit;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
		// assign each targetName of genome of genomes
		for (Genome genome : genomes) {
			genome.setTargetName(targetName);
		}
	}

}
