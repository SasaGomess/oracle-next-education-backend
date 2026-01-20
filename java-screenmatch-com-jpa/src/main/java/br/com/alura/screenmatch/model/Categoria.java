package br.com.alura.screenmatch.model;

public enum Categoria {
    ROMANCE("Romance"),
    DRAMA("Drama"),
    MISTERIO("Mystery"),
    ACAO("Action"),
    COMEDIA("Comedy"),
    CRIME("Crime");

    private String categoriaOmdb;

    Categoria(String categoriaOmdb){
        this.categoriaOmdb = categoriaOmdb;
    }

    public static Categoria fromString(String text){
        for(Categoria categoria : Categoria.values()){
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria foi encontrada");
    }
}
