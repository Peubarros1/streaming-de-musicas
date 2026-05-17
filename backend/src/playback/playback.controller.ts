import { Controller, Get, Post, Body, Param, ParseIntPipe} from '@nestjs/common';
import { PlaybackService } from './playback.service';
import { CreatePlaybackDto } from './dto/create-playback.dto';

@Controller('users/:login/playback')
export class PlaybackController {
  constructor(private readonly playbackService: PlaybackService) {}

  @Post() //cria playbacks
  create(@Body() createPlaybackDto: CreatePlaybackDto) {
    return this.playbackService.create(createPlaybackDto);
  }

  @Get()
  findByUser(@Param('login') login:string,){
    return this.playbackService.findByUser(login);
  }

  @Get(':id')
  findOne(@Param('login') login: string, @Param('id', ParseIntPipe) id: number) {
    return this.playbackService.findOne(login, id,);
  }

  @Get(':type/:id')
  findbytypeandid(@Param('login') login: string, @Param('type') type: 'music' | 'episode', @Param('id', ParseIntPipe) id: number){
    return this.playbackService.findbytype(login, type, id, );
  }

  @Get(':type')
  findbytype(@Param('login') login: string, @Param('type') type: 'music' | 'episode'){
    return this.playbackService.findbytype(login, type);
  }
}
