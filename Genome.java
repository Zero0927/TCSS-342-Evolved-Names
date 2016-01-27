//Siyuan Zhou, Feng Yang
import java.util.Random;

public class Genome {

	public static final Character[] characters = new Character[] { 'A', 'B',
			'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '-',
			'\'' };
	public static final int MAXFITNESS = 100000;
	private double mutationRate;
	private int fitness = Genome.MAXFITNESS;
	private StringBuilder string = new StringBuilder("A");
	private String targetName = null;
	private boolean calculateFitnessflag;//record weather calculate the fitness or not

	/**
	 * 
	 * @param mutationRate
	 */
	public Genome(double mutationRate) {
		this.mutationRate = mutationRate;
		this.calculateFitnessflag = false;
	}

	/**
	 * 
	 * @param gene
	 */
	public Genome(Genome gene) {
		this.mutationRate = gene.getMutationRate();
		this.string = new StringBuilder(gene.getString());
		this.fitness = gene.getFitness();
		this.targetName = gene.getTargetName();
		this.calculateFitnessflag = false;
	}

	/**
	 * mutate the string in this genome
	 */
	public void mutate() {
		Random random = new Random();
		double happenRate = random.nextDouble();
		// with mutationRate chance add a character
		if (happenRate <= mutationRate) {
			addCharRandomly();
		}
		// with mutationRate chance delete a character
		if ((happenRate = random.nextDouble()) <= mutationRate) {
			deleteCharRandomly();
		}
		// for each character in the string
		for (int i = 0, len = string.length(); i < len; i++) {
			// with mutationRate chance the character is replaced
			if ((happenRate = random.nextDouble()) <= mutationRate) {
				replaceCharRandomely(i);
			}
		}
	}

	/**
	 * add a randomly selected character to a randomly selected position in the
	 * string
	 */
	private void addCharRandomly() {
		Random random = new Random();
		int charIndex = random.nextInt(29);
		int addPosition = random.nextInt(string.length() + 1);

		if (addPosition < string.length()) {
			string.insert(addPosition, Genome.characters[charIndex]);
		} else {
			string.append(Genome.characters[charIndex]);
		}
	}

	/**
	 * delete a single character from a randomly selected
	 */
	private void deleteCharRandomly() {
		if (string.length() == 1)
			return;
		Random random = new Random();
		int delPos = random.nextInt(string.length());
		string.deleteCharAt(delPos);
	}

	/**
	 * replace the character in this position by a randomly selected character
	 * 
	 * @param replacePos
	 *            certaion position
	 */
	private void replaceCharRandomely(int replacePos) {
		if (replacePos >= string.length())
			return;
		Random random = new Random();
		int charIndex = random.nextInt(29);
		string.setCharAt(replacePos, Genome.characters[charIndex]);
	}

	/**
	 * update the current Genome by crossing it over with other
	 * 
	 * @param other
	 *            certain Genome
	 */
	public void crossover(Genome other) {
		Random random = new Random();
		// record the length of other's string
		int otherStringLength = other.getString().length();
		for (int i = 0, len = string.length(); i < len; i++) {
			// randomly select a parent string
			int selectedString = random.nextInt(2);

			if (selectedString == 0) {
				// select the local string
				continue;
			} else {
				// select the other string
				if (i < otherStringLength) {
					// replace the character by other's
					string.setCharAt(i, other.getString().charAt(i));
				} else {
					// clear the rest of characters
					string.delete(i, string.length());
					break;
				}
			}
		}
	}

	/**
	 * get the fitness of the genome
	 * 
	 * @return
	 */
	public Integer fitness() {
		int n = string.length(), m = targetName.length();
		int l = n < m ? n : m;
		int fitnessTemp = Math.abs(n - m) * 2;
		for (int i = 0; i < l; i++) {
			if (string.charAt(i) != targetName.charAt(i))
				fitnessTemp = fitnessTemp + 1;
		}
		this.fitness=fitnessTemp;
		this.calculateFitnessflag=true;
		return fitnessTemp;
	}

	/**
	 * use the Wagner-Fischer algorithm for calculating Levenshtein edit
	 * distance
	 * 
	 * @return fitness
	 */
	public Integer fitness2() {
		int n = string.length(), m = targetName.length();

		int D[][] = new int[n + 1][m + 1];

		for (int i = 0, len = n + 1; i < len; i++)
			D[i][0] = i;

		for (int i = 0, len = m + 1; i < len; i++)
			D[0][i] = i;

		for (int i = 1, leni = n + 1; i < leni; i++)
			for (int j = 1, lenj = m + 1; j < lenj; j++){
				if (string.charAt(i - 1) == targetName.charAt(j - 1))
					D[i][j] = D[i - 1][j - 1];
				else {
					int d1 = D[i - 1][j] + 1;
					int d2 = D[i][j - 1] + 1;
					int d3 = D[i - 1][j - 1] + 1;
					D[i][j] = d1 < d2 ? (d1 < d3 ? d1 : d3) : (d2 < d3 ? d2
							: d3);
				}
			}
		int fitnessTemp = D[n][m] + (Math.abs(n - m) + 1) / 2;
		this.fitness=fitnessTemp;
		this.calculateFitnessflag = true;
		return fitnessTemp;
	}

	/**
	 * return the Genome's character string and fitness
	 */
	public String toString() {
		return "(\"" + string.toString() + "\", " + getFitness() + ")";
	}

	/* getters and setters */
	public double getMutationRate() {
		return mutationRate;
	}

	public void setMutationRate(double mutationRate) {
		this.mutationRate = mutationRate;
	}

	public StringBuilder getString() {
		return string;
	}

	public int getFitness() {
		if(!calculateFitnessflag) fitness();
		return this.fitness;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

}
