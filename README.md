# beta-saber 

- [DESCRIPTION](#description)
- [INSTALLATION](#installation)
- [USAGE](#usage)
- [BUGS](#bugs)

# DESCRIPTION

**beta-saber** is a beat saber knock-off made in Eclipse using the Processing core and bash. Beat maps are automatically generated using Minim. Songs are downloaded using Youtube-dl, Spotify-dl, and ffmpeg. The Spotify-web-API java wrapper is used in tandem with Spotify-dl.

# INSTALLATION

**youtube-dl**

- Please visit https://github.com/ytdl-org/youtube-dl for information on installing youtube-dl.

**spotify-dl**

- Please visit https://github.com/SathyaBhat/spotify-dl for information on installing spotify-dl.

**ffmpeg**

- Please visit https://github.com/FFmpeg/FFmpeg for information on installing ffmpeg.

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
  - **Minim**: Asynchronous audio analysis
  - **ControlP5**: tutorial dashboard

  The following Terminal libraries are used:
  - **bash**: shell 
  - **youtube-dl**: download off youtube
  - **spotify-dl**: download off spotify
  - **ffmpeg**: convert from m4a to mp3

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




  




