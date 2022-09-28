package com.umldesigner.infrastructure.uml.logic.api;

/**
 * TODO it might be worth to consider adding a annotation at top of each enum to tell us that it can be used as an
 *  HelperEnum and for that annotation to automatically be implemented in the enum for future use.
 *  this method should improve readability and possibly decrease complexity and repeated code
 */
public enum Endpoints {
    project(Helper.project),
    table(Helper.project, Raw.tableRaw),
    item(Helper.item),
    itemInfo(Helper.item, Raw.itemInfoRaw),
    itemForeignKey(Helper.item, Raw.foreignKeyRaw);

    /**
     * the final address
     */
    private final String address;
    /**
     * enums that were used to assemble the address
     */
    // private final Enum<?>[] usedEnums;

    /**
     * @implSpec this constructor takes the enums in the order that they are added and calls their toString method to
     * assemble the final address.
     * Only enums of type {@linkplain Raw} or {@linkplain Helper} are accepted
     *
     * @see Raw
     * @see Helper
     */
    Endpoints(Enum<?>... enums){
        StringBuilder tempAddress = new StringBuilder();
        for(Enum<?> curEnum : enums){
            if(curEnum instanceof Raw || curEnum instanceof Helper) {
                tempAddress.append(curEnum);
            } else {
                throw new IllegalArgumentException("only instances of " + Helper.class.getSimpleName() + " or " +
                        Raw.class.getSimpleName() + " are accepted");
            }
        }
        // this.usedEnums = enums;
        this.address = tempAddress.toString();
    }

    @Override
    public String toString() {
        return address;
    }

    public enum Raw {
        schema("/s"),
        projectRaw("/project"),
        tableRaw("/table"),
        itemRaw("/item"),
        itemInfoRaw("/info"),
        foreignKeyRaw("/foreignKey");

        private final String address;
        Raw(String address){
            this.address = address;
        }

        @Override
        public String toString() {
            return address;
        }
    }

    /**
     * helper enum used for avoiding repeating of code, this code should not be visible to the end user and should only
     * be used in the {@linkplain TestEnum}
     */
    enum Helper {
        project(Raw.schema, Raw.projectRaw),
        item(Raw.schema, Raw.projectRaw, Raw.itemRaw);

        private final String address;

        Helper(Enum<?>... enums){
            StringBuilder tempAddress = new StringBuilder();
            for(Enum<?> curEnum : enums){
                tempAddress.append(curEnum);
            }
            this.address = tempAddress.toString();
        }

        @Override
        public String toString() {
            return address;
        }
    }
}
