<img src="src/main/resources/icon.png" alt="Notegrant Icon" width="20%" />

# Notegrant for Windows

> **_The Official/Original Project_**
> This repository is optimized for _Visual Studio Code_ users or similar whom use the `.vscode` folder.

Hi there! This is the official repository of _Notegrant for Windows_. Here you can find the _Maven+Java_ source code that makes up the highly-customizable editor. You will also find resources as seen in the app, such as the built-in themes (made in CSS) and interfaces (which are written in an XML-like format.) This repository also serves as the developer codebase of _Notegrant for Windows_, open-sourced so anyone (including you) can add on or fix features! Note that only the project owner can produce binaries known as _official_, but user-created builds are allowed under the terms that **a)** you advertise your build as _non-official_ and **b)** you leave credit towards the original project (besides edits made by you,) and **c)** the contents of the build are not dangerous to certain softwares and hardwares.

## Features

- Configuration file at _**{home}**/Notegrant Data/config.properties_.
- A `themes` directory next to the configuration file which can be used for customized themes that are not built-in.
- Default document storage directory at _**{home}**/My Notegrants/config.properties_.

## Built-In Themes

In the configuration file, part of it should look like this:

```properties
theme=light
```

Here, you can configure the theme. By default, these are available:

```list
light
dark
cool-purple
```

But if you placed a file named `blackout.css` into the `themes` directory, you can do this:

```properties
theme=blackout
```

See the guide [How to Make a Theme](docs/How%20to%20Make%20a%20Theme.md) for creating one of those fancy things!

## Builds for Other Systems

If you have or know someone who has a version of this app ported over to a system besides _Windows_, please edit this file to list it here. Be sure to include your project's name (preferably \*Notegrant for **SysName\***) and a link to the project's public GitHub and the download(s) link(s).

> Builds that have added-on features or removed features that don't need to be removed will be deleted from this list!

## License

This repository is under the [MIT License](LICENSE.md).
