# beta-saber 

https://user-images.githubusercontent.com/85639422/138577567-37e4717f-9e7d-4cd2-a1d5-f629b407b76c.mov

- [DESCRIPTION](#description)
- [INSTALLATION](#installation)
- [USAGE](#usage)
- [BUGS](#bugs)

# DESCRIPTION

**beta-saber** is a beat saber knock-off made in Eclipse using the Processing core and bash. Beat maps are automatically generated using Minim. Songs are downloaded using youtube-dl, spotify-dl, and FFmpeg. The spotify-web-API java wrapper is used in tandem with spotify-dl.

# INSTALLATION

**youtube-dl**

- Please visit https://github.com/ytdl-org/youtube-dl for information on installing youtube-dl.

**spotify-dl**

- Please visit https://github.com/SathyaBhat/spotify-dl for information on installing spotify-dl.

**ffmpeg**

- Please visit https://github.com/FFmpeg/FFmpeg for information on installing FFmpeg.

**spotify-web-api-java**

- Please visit https://github.com/spotify-web-api-java/spotify-web-api-java for information on installing the Spotify Api Java Wrapper.

**Processing**

- Please visit https://processing.org/ to download Processing 3.5.4.

**Eclipse**

- Please visit https://www.eclipse.org/ide/ to download Eclipse 4.20.0 (2021-06).

**Libraries**

  The following Processing libraries are used:
  - **Sound**: music playback
  - **Video**: color tracking for "sabers"
  - **Minim**: Asynchronous audio analysis and music visualizer
  - **ControlP5**: tutorial dashboard

  The following bash libraries are used:
  - **youtube-dl**: download off YouTube
  - **spotify-dl**: download off Spotify
  - **FFmpeg**: convert from m4a to mp3

  The following Java libraries are used:
  - **Color**: color
  - **Util**: ArrayList
  - **File**: manage download songs
  - **PrintWriter**: Java bash wrapper
  - **io**: InputStream and OutputStream needed for PrintWriter

  # USAGE
  
  **ClientCredentialsExample.java**
  - Use Client ID and Client Secret to obtain temporary access token needed for searching Spotify

  **SearchTracksExample.java**
  - Use access token to search Spotify for song URL. 

  **SyncPipe.java**
  - implements runnable
  - InputStream and OutputStream for PrintWriter

  **MySketch2.java**
  - tutorial
  - adjust color, note speed, color tracking threshold, game difficulty, motion sensitivity
  - type in song and download using spotify or youtube
  - launch MySketch.java with above data

  **MySketch.java**
  - like MySketch2.java, but with the adjusted settings and selected song.
  - Prefers MySketch2.java to be run first, but the appropriate fields can be manually set to skip the tutorial 

   # BUGS
   - youtube-dl and spotify-dl occasionally download very slowly https://github.com/ytdl-org/youtube-dl/issues/15271
   - [Intentional] MySketch.java will not download songs over 10 minutes. 
   - When setting the colors to be tracked, clicking somewhere other than the displayed video feed may cause MySketch2.java to crash.




