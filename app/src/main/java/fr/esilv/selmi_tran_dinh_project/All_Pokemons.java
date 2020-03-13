package fr.esilv.selmi_tran_dinh_project;

import java.util.List;

public class All_Pokemons {
    private int count;
    private String next;
    private String previous;

    public class Results
    {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }

    private List<Results> results;

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<Results> getResults() {
        return results;
    }
}
