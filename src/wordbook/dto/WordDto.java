package wordbook.dto;

public class WordDto {
    int id;
    String word;
    String mean;
    boolean memorized;
    int collectionId;

    public WordDto(int id, String word, String mean, boolean memorized, int collectionId) {
        this.id = id;
        this.word = word;
        this.mean = mean;
        this.memorized = memorized;
        this.collectionId = collectionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public boolean getMemorized() {
        return memorized;
    }

    public void setMemorized(boolean memorized) {
        this.memorized = memorized;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {

    }

}
