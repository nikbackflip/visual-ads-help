export const light = {
    palette: {
        primary: {
            light: '#efefef',
            main: '#bdbdbd',
            dark: '#8d8d8d',
            contrastText: '#000000',
        },
        secondary: {
            light: '#ff6434',
            main: '#a30000',
            dark: '#a30000',
            contrastText: '#ffffff',
        },
        background: {
            default: '#F2F2F2'
        }
    }
};

const lightDrawing = {
    edge: "#757575",
        selected: "#ff3d00",
        text: "#323232",
        colors: [
            '#607d8b',
            '#9e9e9e',
            '#795548',
            '#cbad80'
    ],
    codeMirrorStyle: "default"
}

export const dark = {
    palette: {
        primary: {
            light: '#545758',
            main: '#3c3c3c',
            dark: '#1a1a1a',
            contrastText: '#ffffff',
        },
        secondary: {
            dark: '#40a8b5',
            main: '#40a8b5',
            contrastText: '#000000',
        },
        background: {
            paper: '#545758',
            default: '#3c3c3c'
        }
    }
};

const darkDrawing = {
    edge: "#000000",
    selected: "#4bacb8",
    text: "#000000",
    colors: [
        '#FF7EC7',
        '#BF42FF',
        '#8A4BFF',
        '#4C80FF'
    ],
    codeMirrorStyle: "darcula"
}

export default function getDrawingColors(theme) {
    if (theme === "dark") return darkDrawing;
    return lightDrawing;

}
