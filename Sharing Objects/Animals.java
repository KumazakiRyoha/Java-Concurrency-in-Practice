import java.util.*;

public class Animals {

    Ark ark;
    Species species;
    Gender gender;

    public int loadTheArk(Collection<Animal> candidates) {
        SortedSet<Animal> animals;
        int numPairs = 0;
        Animal candidate = null;

        // animals confined to method, don't let them escape!
        animals = new TreeSet<>(new SpeciesGenderComparator());
        animals.addAll(candidates);
        for (Animal a : animals) {
            if (candidate == null || !candidate.isPotentialMate(a)) {
                candidate = a;
            } else {
                ark.load(new AnimalPair(candidate,a));
                ++numPairs;
                candidate = null;
            }
        }
        return numPairs;
    }


    class Ark {
        private final Set<AnimalPair> loadedAnimals = new HashSet<>();

        public void load(AnimalPair pair) {
            loadedAnimals.add(pair);
        }
    }

    class AnimalPair {
        private final Animal one,two;

        public AnimalPair(Animal one,Animal two) {
            this.one = one;
            this.two = two;
        }
    }

    class SpeciesGenderComparator implements Comparator<Animal> {

        @Override
        public int compare(Animal o1, Animal o2) {
            int speciesCompare  = o1.species.compareTo(o2.species);
            return (speciesCompare != 0)
                    ? speciesCompare
                    : o1.gender.compareTo(o2.gender);
        }
    }

    class Animal {
        Species species;
        Gender gender;
        public boolean isPotentialMate(Animal other) {
            return species == other.species && gender != other.gender;
        }
    }

    enum Gender {
        MALE,FEMALE
    }
    enum Species {
        AARDVARK, BENGAL_TIGER, CARIBOU, DINGO, ELEPHANT, FROG, GNU, HYENA,
        IGUANA, JAGUAR, KIWI, LEOPARD, MASTADON, NEWT, OCTOPUS,
        PIRANHA, QUETZAL, RHINOCEROS, SALAMANDER, THREE_TOED_SLOTH,
        UNICORN, VIPER, WEREWOLF, XANTHUS_HUMMINBIRD, YAK, ZEBRA
    }
}
