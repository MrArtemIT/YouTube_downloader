import time
import sys
import pytube
import os
from pytube import Playlist
from pytubefix import Playlist
import re
#from moviepy.editor import VideoFileClip, AudioFileClip, concatenate_videoclips

dire = ("/storage/emulated/0/Download")
def main(url, fix_mode):


    if url=="test":
        time.sleep(0.5)
    elif fix_mode == "true":
        playlist = Playlist(url)
        playlist._video_regex = re.compile(r"\"url\":\"(/watch\?v=[\w-]*)")
        for video in playlist.videos:
            video.streams. \
                filter(type='video', progressive=True, file_extension='mp4'). \
                order_by('resolution'). \
                desc(). \
                first(). \
                download(dire)
    else:



        playlist = Playlist(url)
        playlist._video_regex = re.compile(r"\"url\":\"(/watch\?v=[\w-]*)")  
        for video in playlist.videos:
            print('downloading : {} with url : {}'.format(video.title, video.watch_url))
            video.streams.\
            filter(type='video', progressive=True, file_extension='mp4').\
            order_by('resolution').\
            desc().\
            first().\
            download(dire)