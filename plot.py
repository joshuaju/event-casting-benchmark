import pandas as pd
import matplotlib.pyplot as plt

# print complete dataframe
pd.set_option('display.max_rows', None)
pd.set_option('display.max_columns', None)
pd.set_option('display.width', None)
pd.set_option('display.max_colwidth', 200)


# read data
df = pd.read_csv("casting-benchmark.csv", index_col='Benchmark')
print(df)


# transform
ss = df[df['Mode'] == 'ss']['Score'].rename("singleshot").sort_index()
ss_err = df[df['Mode'] == 'ss']['Error'].rename("singleshot_error").sort_index()
avg = df[df['Mode'] == 'avgt']['Score'].rename("average").sort_index()
avg_err = df[df['Mode'] == 'avgt']['Error'].rename("average_error").sort_index()
plotdata = pd.concat([ss, ss_err, avg, avg_err], axis=1)
plotdata.index.name = None
print(plotdata)


# plot
ax = plotdata.plot.barh(
    y=['singleshot', 'average'],
    subplots=True,
    sharex=False,
    legend=False)
fig = ax[0].figure
ax[1].set(xlabel="(µs per operation)")
plt.suptitle("Casting 10,000 events")
plt.tight_layout()


# save
plt.savefig("casting-benchmark-plot.png")