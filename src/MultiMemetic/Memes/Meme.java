package MultiMemetic.Memes;

/**
 * Class which represents a meme.
 */
public class Meme {

    private int m_Option;
    private int m_TotalOptions;

    /**
     * Gets the meme option from the meme.
     *
     * @return an int, which is the meme option.
     */
    public int getMemeOption() {
        return m_Option;
    }

    /**
     * Sets the meme option from the meme.
     *
     * @param memeOption an int, which is the meme option.
     */
    public void setMemeOption(int memeOption) {
        if (memeOption < m_TotalOptions) {
            m_Option = memeOption;
        }
    }

    /**
     * Gets the total number of options in a meme.
     *
     * @return an int, which is the total options.
     */
    public int getTotalOptions() {
        return m_TotalOptions;
    }

    /**
     * Sets the total number of options in a meme.
     *
     * @param totalOptions an int, which is total options in a meme.
     */
    private void setTotalOptions(int totalOptions) {
        m_TotalOptions = totalOptions;
    }

    /**
     * Constructor which initialises the meme to a specific option, but also
     * stored the total number of options this meme could have.
     *
     * @param memeOption an int, which is the meme option that is selected.
     * @param totalOptions an int, which is the total number of options.
     */
    public Meme(int memeOption, int totalOptions) {
        setMemeOption(memeOption);
        setTotalOptions(totalOptions);
    }
}
