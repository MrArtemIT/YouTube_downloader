import pytube
import os
#from moviepy.editor import VideoFileClip, AudioFileClip, concatenate_videoclips


def main(url, item):


    dire = ("/storage/emulated/0/Download")

    yt = pytube.YouTube(url)
    #ft = yt.title

    if item=="Video":
        vid = yt.streams.get_highest_resolution()
        vid.download(dire)
    else:
        vid = yt.streams.filter(only_audio=True).desc().first()
        out_file = vid.download(dire)
        # save the file
        base, ext = os.path.splitext(out_file)
        new_file = base + '.mp3'
        os.rename(out_file, new_file)


        '''vt = yt.title
        vid = yt.streams.filter(res='1080p').desc().first()
        au = yt.streams.filter(only_audio=True).desc().first()
        out_file = vid.download(dire)
        # save the file
        base, ext = os.path.splitext(out_file)
        new_file = base + '.mp3'
        os.rename(out_file, new_file)
        au.download(dire)'''
        '''au.download(dire)
        vid.download(dire)'''

        '''input_video = ffmpeg.input('/storage/emulated/0/Download/' + vt + '.mp4')

        input_audio = ffmpeg.input('/storage/emulated/0/Download/'+ vt + '.webm')

        ffmpeg.concat(input_video, input_audio, v=1, a=1).output('/storage/emulated/0/Download/finished_video.mp4').run()'''
        '''videoclip = VideoFileClip(vid.download(dire))
        audioclip = AudioFileClip(new_file)

        new_audioclip = CompositeAudioClip([audioclip])
        videoclip.audio = new_audioclip
        videoclip.write_videofile("new_filename.mp4")'''



#va = yt.author
    #vd = yt.description

    #vid.download(dire)
