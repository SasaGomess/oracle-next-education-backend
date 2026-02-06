package br.com.alura.screenmatch.model;

public enum Categoria {
    ROMANCE("Romance", "Romance"),
    DRAMA("Drama", "Drama"),
    MISTERIO("Mystery", "Mistério"),
    ACAO("Action", "Ação"),
    COMEDIA("Comedy", "Comédia"),
    CRIME("Crime", "Crime");

    private String categoriaOmdb;
    private String categoriaEmPortugues;

    Categoria(String categoriaOmdb, String categoriaEmPortugues) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaEmPortugues = categoriaEmPortugues;
    }

    public static Categoria fromString(String text){
        for(Categoria categoria : Categoria.values()){
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria foi encontrada pela string fornecida" + text);
    }
    public static Categoria fromPortugues(String text){
        for(Categoria categoria : Categoria.values()){
            if (categoria.categoriaEmPortugues.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria foi encontrada pela string fornecida" + text);
    }
}
