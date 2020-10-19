/*
 * FileSelectComponent
 *
 * Copyright (c) 2020 desktopgame
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package jp.desktopgame.fsc;

import javax.swing.filechooser.FileFilter;

/**
 *
 * @author desktopgame
 */
public enum StandardFileFilters {
    TEXT() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.txt", (f) -> f.getName().endsWith(".txt"));
        }
    }, CSS() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.css", (f) -> f.getName().endsWith(".css"));
        }
    }, HTML() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.html", (f) -> f.getName().endsWith(".html"));
        }
    }, MARKDOWN() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.md", (f) -> f.getName().endsWith(".md"));
        }
    }, PHP() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.php", (f) -> f.getName().endsWith(".php"));
        }
    }, RUBY() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.rb", (f) -> f.getName().endsWith(".rb"));
        }
    }, PYTHON() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.py", (f) -> f.getName().endsWith(".py"));
        }
    }, JAVASCRIPT() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.js", (f) -> f.getName().endsWith(".js"));
        }
    }, TYPESCRIPT() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.ts", (f) -> f.getName().endsWith(".ts"));
        }
    }, JAVA() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.java", (f) -> f.getName().endsWith(".java"));
        }
    }, CSHARP() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.cs", (f) -> f.getName().endsWith(".cs"));
        }
    }, C() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.c", (f) -> f.getName().endsWith(".c"));
        }
    }, HEADER() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.h", (f) -> f.getName().endsWith(".h"));
        }
    }, CPP() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.cpp", (f) -> f.getName().endsWith(".cpp"));
        }
    }, CPP_HEADER() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.hpp", (f) -> f.getName().endsWith(".hpp"));
        }
    }, SCALA() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.scala", (f) -> f.getName().endsWith(".scala"));
        }
    }, JSON() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.json", (f) -> f.getName().endsWith(".json"));
        }
    }, XML() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.xml", (f) -> f.getName().endsWith(".xml"));
        }
    }, INI() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.ini", (f) -> f.getName().endsWith(".ini"));
        }
    }, TOML() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.toml", (f) -> f.getName().endsWith(".toml"));
        }
    }, YAML() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.yaml", (f) -> f.getName().endsWith(".yaml"));
        }
    }, JPG() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.jpg", (f) -> f.getName().endsWith(".jpg"));
        }
    }, JPEG() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.jpeg", (f) -> f.getName().endsWith(".jpeg"));
        }
    }, GIF() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.gif", (f) -> f.getName().endsWith(".gif"));
        }
    }, PNG() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.png", (f) -> f.getName().endsWith(".png"));
        }
    }, BMP() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.bmp", (f) -> f.getName().endsWith(".bmp"));
        }
    }, MIDI() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.mid", (f) -> f.getName().endsWith(".mid") || f.getName().endsWith(".smf"));
        }
    }, WAVE() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.wav", (f) -> f.getName().endsWith(".wav"));
        }
    }, MP3() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.mp3", (f) -> f.getName().endsWith(".mp3"));
        }
    }, MP4() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.mp4", (f) -> f.getName().endsWith(".mp4"));
        }

    }, EXE() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.exe", (f) -> f.getName().endsWith(".exe"));
        }
    }, JAR() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.jar", (f) -> f.getName().endsWith(".jar"));
        }
    }, ZIP() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.zip", (f) -> f.getName().endsWith(".zip"));
        }
    }, RAR() {
        @Override
        public FileFilter newFileFilter() {
            return new GenericFileFilter("*.rar", (f) -> f.getName().endsWith(".rar"));
        }
    };

    ;

    public abstract FileFilter newFileFilter();
}
