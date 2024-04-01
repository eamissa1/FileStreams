import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class Product implements Serializable
{
    private String ID;
    private String name;
    private String description;
    private double cost;

    public Product(String ID, String name, String description, double cost)
    {
        this.name = name;
        this.description = description;
        this.ID = ID;
        this.cost = cost;
    }

    public String getPaddedID()
    {
        return padString(ID, 6);
    }

    public String getPaddedName()
    {
        return padString(name, 35);
    }

    public String getPaddedDescription()
    {
        return padString(description, 75);
    }

    private String padString(String input, int length)
    {
        if (input.length() > length) {
            return input.substring(0, length);
        }
        return String.format("%-" + length + "s", input);
    }

    public String trimString(String input)
    {
        return input.trim();
    }

    public byte[] toByteArray()
    {
        String record = getPaddedID() + getPaddedName() + getPaddedDescription() + String.format("%.2f", cost);
        return record.getBytes(StandardCharsets.UTF_8);
    }
}
