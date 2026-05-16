import { Entity, PrimaryGeneratedColumn, Column, ManyToOne,
  ManyToMany,
  JoinTable } from 'typeorm';
import {User} from '../users/entities/user.entity';
import {Musica} from '../musicas/musica.entity';
import { Album } from '../albuns/album.entity';

@Entity('artistas')
export class Artista extends User {
    @Column() 
    nomeArtistico: string;

    @ManyToMany(() => Album, album => album.artistas)
    albuns: Album[];

    @ManyToMany(() => Musica, musica => musica.artistas)
    musicas: Musica[];
}