import matplotlib.pyplot as plt
import pandas as pd
import seaborn as sns


def configure_pandas():
    # print complete dataframe
    pd.set_option('display.max_rows', None)
    pd.set_option('display.max_columns', None)
    pd.set_option('display.width', None)
    pd.set_option('display.max_colwidth', 200)


def read_data(input):
    df = pd.read_table(input, delim_whitespace=True)
    df.rename(columns={"(SHUFFLE)": "Shuffle", 'Score': 'Score (ms)'}, inplace=True)
    df['Benchmark'] = df['Benchmark'].map(lambda name: (name.split('.')[1]))  # remove class name from benchmark name
    df['Mode'] = df['Mode'].map({'avgt': 'averaged', 'ss': 'single shot'})
    df['Shuffle'] = df['Shuffle'].map({'off': 'off', 'shuffle': 'Collections#shuffle', 'random': 'Random#nextInt'})
    return df


def plot(plotdata, output):
    cat = sns.catplot(
        kind='bar',
        data=plotdata,
        x='Score (ms)',
        y='Benchmark',
        col='Mode',
        hue='Shuffle',
        sharex=False,
        legend=False,
        palette="pastel"
    )
    cat.fig.subplots_adjust(top=0.85, bottom=0.25)
    cat.fig.suptitle("Casting 10.000 events")
    plt.legend(title="Shuffle",loc='center', bbox_to_anchor=(0, -0.3), ncol=3)
    plt.savefig(output)
    plt.show()


configure_pandas()
data = read_data("casting-benchmark-shuffled.txt")
plot(data, "casting-benchmark.png")