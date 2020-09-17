package themovies.helpers.models;

import java.util.Objects;

public class Info {
    private String version;
    private String name;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode() {
        return Objects.hash(name, version);
    }
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Info))
            return false;
        Info other = (Info) obj;
        return Objects.equals(name, other.name) && Objects.equals(version, other.version);
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     */
    public Info() {
    }

    /**
     * @param version
     * @param name
     */
    public Info(String version, String name) {
        this.version = version;
        this.name = name;
    }

}
