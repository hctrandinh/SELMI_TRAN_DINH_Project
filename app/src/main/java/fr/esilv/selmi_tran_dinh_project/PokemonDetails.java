package fr.esilv.selmi_tran_dinh_project;

import java.util.List;

public class PokemonDetails {
    public class Abilities
    {
        public class Ability
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

        private Ability ability;
        private boolean is_hidden;
        private int slot;

        public Ability getAbility() {
            return ability;
        }

        public boolean isIs_hidden() {
            return is_hidden;
        }

        public int getSlot() {
            return slot;
        }
    }
    private List<Abilities> abilities;

    public List<Abilities> getAbilities() {
        return abilities;
    }

    private int base_experience;
    private int id;
    private String name;

    public int getBase_experience() {
        return base_experience;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public class Sprites
    {
        private String back_default;
        private String back_female;
        private String back_shiny;
        private String back_shiny_female;
        private String front_default;
        private String front_female;
        private String front_shiny;
        private String front_shiny_female;

        public String getBack_default() {
            return back_default;
        }

        public String getBack_female() {
            return back_female;
        }

        public String getBack_shiny() {
            return back_shiny;
        }

        public String getBack_shiny_female() {
            return back_shiny_female;
        }

        public String getFront_default() {
            return front_default;
        }

        public String getFront_female() {
            return front_female;
        }

        public String getFront_shiny() {
            return front_shiny;
        }

        public String getFront_shiny_female() {
            return front_shiny_female;
        }
    }

    private Sprites sprites;

    public Sprites getSprites() {
        return sprites;
    }

    public class Stats
    {
        private int base_stat;
        private int effort;

        public int getBase_stat() {
            return base_stat;
        }

        public int getEffort() {
            return effort;
        }

        public class Stat
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

        private Stat stat;

        public Stat getStat() {
            return stat;
        }
    }

    private List<Stats> stats;

    public List<Stats> getStats() {
        return stats;
    }

    public class Types
    {
        private int slot;

        public int getSlot() {
            return slot;
        }

        public class Type
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

        private Type type;

        public Type getType() {
            return type;
        }
    }

    private List<Types> types;

    public List<Types> getTypes() {
        return types;
    }

    private int weight;

    public int getWeight() {
        return weight;
    }
}
