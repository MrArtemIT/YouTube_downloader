import time
import sys
import pytube
import os
#from moviepy.editor import VideoFileClip, AudioFileClip, concatenate_videoclips


def main(url, item_static):


    if url=="test":
        time.sleep(0.5)
    else:

        dire = ("/storage/emulated/0/Download")

        yt = pytube.YouTube(url)
        #ft = yt.title

        vid = yt.streams.filter(only_audio=True).desc().first()
        out_file = vid.download(dire)
        # save the file
        base, ext = os.path.splitext(out_file)
        new_file = base + '.mp3'
        os.rename(out_file, new_file)
